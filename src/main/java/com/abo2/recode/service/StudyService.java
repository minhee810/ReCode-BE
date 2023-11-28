package com.abo2.recode.service;

import com.abo2.recode.domain.post.Post;
import com.abo2.recode.domain.skill.Skill;
import com.abo2.recode.domain.skill.SkillRepository;
import com.abo2.recode.domain.skill.StudySkill;
import com.abo2.recode.domain.skill.StudySkillRepository;
import com.abo2.recode.domain.studymember.StudyMember;
import com.abo2.recode.domain.studymember.StudyMemberRepository;
import com.abo2.recode.domain.studyroom.StudyRoomRepository;
import com.abo2.recode.domain.studyroom.StudyRoom;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserRepository;
import com.abo2.recode.dto.post.PostRespDto;
import com.abo2.recode.dto.study.StudyReqDto;
import com.abo2.recode.dto.study.StudyResDto;
import com.abo2.recode.handler.ex.CustomApiException;
import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudyService {

    private StudyRoomRepository studyRoomRepository;

    private StudySkillRepository studySkillRepository;

    private SkillRepository skillRepository;

    private UserRepository userRepository;

    private StudyMemberRepository studyMemberRepository;

    @Autowired
    public StudyService(StudyRoomRepository studyRoomRepository,
                        StudySkillRepository studySkillRepository,
                        SkillRepository skillRepository,
                        UserRepository userRepository,
                        StudyMemberRepository studyMemberRepository) {
        this.studyRoomRepository = studyRoomRepository;
        this.studySkillRepository = studySkillRepository;
        this.skillRepository = skillRepository;
        this.userRepository = userRepository;
        this.studyMemberRepository = studyMemberRepository;
    }

    //스터디 조장의 스터디 멤버 승인/거부
    @Transactional
    public void membershipUpdate(String status, Long studyId, Long userId) {

        if(status.equals("Approved")){
            //StudyMember 테이블 업데이트(상태값 0 -> 1)
            studyMemberRepository.membershipUpdate(1,studyId,userId);

        } else if (status.equals("Rejected")) {
            //StudyMember 테이블 업데이트(상태값 0 -> 2)
            studyMemberRepository.membershipUpdate(2,studyId,userId);
        }
    }

    public void createRoom(StudyReqDto.StudyCreateReqDto studyCreateReqDto){

        System.out.println("Service createRoom()");

        //1. 넘겨 받은 studyReqDto에서 정보 가져오기
        User master = userRepository.findById(studyCreateReqDto.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        String study_name = studyCreateReqDto.getStudyName();
        String title = studyCreateReqDto.getTitle();
        String description = studyCreateReqDto.getDescription();
        LocalDate start_date = studyCreateReqDto.getStartDate();
        LocalDate end_date = studyCreateReqDto.getEndDate();
        Integer current_num = 1; //스터디 그룹 현재 인원은 기본 1명으로 설정
        Integer max_num = studyCreateReqDto.getMaxNum();

        //1-1. start_time,end_time String -> LocalDateTime
        LocalDateTime startDateTime = convertToDateTime(studyCreateReqDto.getStartTime());
        LocalDateTime endDateTime = convertToDateTime(studyCreateReqDto.getEndTime());

        //2. DB에 전송할 studyRoom Entity 선언, studyRoom Entity에 데이터 집어 넣기, DB에 Insert
        StudyRoom studyRoom = StudyRoom.builder()
                .studyName(study_name)
                .title(title)
                .description(description)
                .startTime(startDateTime)
                .endTime(endDateTime)
                .startDate(start_date)
                .endDate(end_date)
                .currentNum(current_num)
                .maxNum(max_num)
                .master(master)
                .build();

        studyRoomRepository.save(studyRoom);

        //3.study_skill 테이블에 skill 삽입
        //3-1 Study_skill Entity 선언, Study_skill Entity에 데이터 집어 넣기, DB에 Insert
        // expertise
        for (String skillName : studyCreateReqDto.getSkills()) {
            Skill skill = skillRepository.findBySkillName(skillName); // 스킬 이름으로 Skill 엔티티 검색

            StudySkill studySkill = StudySkill.builder()
                    .studyRoom(studyRoom)
                    .skill(skill)
                    .build();
            studySkillRepository.save(studySkill);
        }
        System.out.println("Service createRoom() save !!!!!!");

        //4. Study_member에 만든 사람(조장) 추가 하기
        StudyMember studyMember = StudyMember.builder()
                .studyRoom(studyRoom)
                .user(master)
                .status(1)
                .build();

        studyMemberRepository.save(studyMember);

    } //createRoom()

    // 문자열을 LocalDateTime 객체로 변환
    private LocalDateTime convertToDateTime(String dateTimeStr) {
        String[] parts = dateTimeStr.split(" ");
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(parts[0].toUpperCase());
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sameWeekDay = now.with(TemporalAdjusters.nextOrSame(dayOfWeek));
        LocalDateTime time = LocalDateTime
                .parse(sameWeekDay.format(DateTimeFormatter.ISO_LOCAL_DATE)
                        + "T" + parts[1], DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        return time;
    }//convertToDateTime()

    // 반환 타입이 Long인지 확인하는 메소드
    public boolean checkReturnType(Object returnValue) {
        return returnValue instanceof Long;
    }//checkReturnType()

    //study 가입 신청
    public StudyResDto.StudyRoomApplyResDto studyApply(StudyReqDto.StudyApplyReqDto studyApplyReqDto) {

        //  studyApplyReqDto
//        @NotEmpty
//        Long study_id;
//
//        @NotEmpty
//        Long user_id;
        
        // -1. user_id,study_id를 기반으로 먼저 유저가 이미 가입했거나 가입한 상태인지 체크
        if(
                checkReturnType(studyRoomRepository.findIdByuser_idAndStudy_id(studyApplyReqDto.getStudy_id(),
                        studyApplyReqDto.getUser_id()))
        ){
            //return new StudyResDto.StudyRoomApplyResDto(studyApplyReqDto.getStudy_id());
        }

            //0. DB에 저장할 스터디룸 엔티티를 study_id를 기반으로 가져와야 함.
            StudyRoom studyRoom;
            Optional<StudyRoom> optionalStudyRoom;

            optionalStudyRoom =
                    studyRoomRepository.findById(studyApplyReqDto.getStudy_id());
            studyRoom = optionalStudyRoom.orElse(null);

            // 0.5 스터디룸이 Null인 경우 -> 비어있는 스터디룸


            // 1.DB에 저장할 User 엔티티를 User_id를 기반으로 가져와야 함.
            Optional<User> optionalUser = userRepository.findById(studyApplyReqDto.getUser_id());

            User user = optionalUser.orElse(null); // Provide a default value (null in this case)

            // 2. DB에 저장할 Study_Member Entity 선언,save
            StudyMember studyMember = StudyMember.builder()
                    .studyRoom(studyRoom)
                    .user(user)
                    .status(0) //not approved
                    .build();

            studyMemberRepository.save(studyMember);

            return new StudyResDto.StudyRoomApplyResDto(studyRoom.getId());

    }//studyApply()

    //스터디 모임 상세 조회
    @Transactional
    public StudyResDto.StudyRoomDetailResDto studyRoomDetailBrowse(Long study_room_id) {
        StudyRoom studyRoom = studyRoomRepository.findWithMasterAndSkillsById(study_room_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 스터디룸이 없습니다. id=" + study_room_id));

        List<StudySkill> studySkills = studySkillRepository.findByStudyRoomId(study_room_id);
        return new StudyResDto.StudyRoomDetailResDto(studyRoom, studySkills);
    }

    // 스터디 탈퇴
    @Transactional
    public void withdrawStudy(Long userId, Long study_room_id){
        // 1. 해당 스터디룸 정보 가져오기
        Optional<StudyRoom> studyRoom = studyRoomRepository.findById(study_room_id);

        if(studyRoom.isPresent()) {
            StudyRoom room = studyRoom.get();

            if(!room.getMaster().equals(userId)){
                studyMemberRepository.deleteByUserIdAndStudyRoomId(userId, study_room_id);
            } else {
                throw new CustomApiException("스터디 장은 탈퇴가 불가능합니다. 권한을 양도한 후에 시도해 주시기 바랍니다.");
            }
        } else {
            throw new CustomApiException("스터디를 찾을 수 없습니다.");
        }
    }

    // 스터디 목록 불러오기
    @Transactional
    public List<StudyResDto.StudyListRespDto> mainList(){
        List<StudyRoom> studyRooms = studyRoomRepository.findAllWithMaster();
        return studyRooms.stream()
                .map(studyRoom -> new StudyResDto.StudyListRespDto(studyRoom, getStudySkills(studyRoom)))
                .collect(Collectors.toList());
    }//mainList()

    // Study_skill 불러오기
    private List<StudySkill> getStudySkills(StudyRoom studyRoom) {
        return studySkillRepository.findByStudyRoomId(studyRoom.getId());
    }//getStudySkills()



    //관리자 스터디 그룹 관리 페이지에서 스터디 멤버 목록 조회
    public List<PostRespDto.StudyMemberListDto> postStudyMemberListInAdminPage(Long studyId) {

        List<StudyMember> studyMembers = studyMemberRepository.findApprovedMemberById(studyId);

       /* if (studyMembers.isEmpty()) {
            throw new CustomApiException("멤버가 존재하지 않습니다.");
        }*/

        return studyMembers.stream()
                .map(PostRespDto.StudyMemberListDto::new)
                .collect(Collectors.toList());

        /*
        조회된 게시물은 List<StudyMember> 형태로 반환
        stream() 메서드를 사용하여 이 리스트를 스트림으로 변환합니다.
        스트림을 사용하면 데이터를 조작하고 변환할 수 있는 다양한 기능을 사용할 수 있습니다.

        .map(PostRespDto.StudyMemberListDto::new)
        map() 메서드를 사용하여 각 StudyMember를 PostRespDto.StudyMemberListDto로 반환. DTO로의 매핑 작업
        PostRespDto.StudyMemberListDto::new : StudyMemberListDto의 생성자를 이용해 StudyMember를 해당 DTO로 변환

        collect(Collectors.toList()):
        collect() 메서드를 사용하여 스트림의 요소들을 다시 리스트로 수집합니다.
        이 때, Collectors.toList()를 사용하여 스트림의 요소들을 리스트로 변환합니다.

        최종적으로 변환된 List<PostRespDto.StudyMemberListDto>가 반환됩니다.
        */

    }//postStudyMemberListInAdminPage()


    public List<StudyResDto.ApplicationResDto> applications(Long groupId) {

        return studyMemberRepository.applications(groupId);
    }

    public StudyResDto.ApplicationEssayResDto applicationsEssay(Long groupId, Long userId) {

        return studyMemberRepository.applicationsEssay(groupId,userId);
    }

    public StudyRoom findStudyRoomById(Long studyRoomId) {
        return studyRoomRepository.findById(studyRoomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 스터디룸을 찾을 수 없습니다."));
    }

    public boolean isUserInStudyRoom(User user, StudyRoom studyRoom) {
        Optional<StudyMember> studyMember = studyMemberRepository.findByUserAndStudyRoom(user, studyRoom);
        return studyMember.isPresent();
    }

}//class StudyService
