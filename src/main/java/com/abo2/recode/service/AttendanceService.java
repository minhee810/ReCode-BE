package com.abo2.recode.service;

import com.abo2.recode.domain.attendance.Attendance;
import com.abo2.recode.domain.attendance.AttendanceDay;
import com.abo2.recode.domain.attendance.AttendanceRepository;
import com.abo2.recode.domain.studymember.StudyMember;
import com.abo2.recode.domain.studymember.StudyMemberRepository;
import com.abo2.recode.domain.studyroom.StudyRoom;
import com.abo2.recode.domain.studyroom.StudyRoomRepository;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserRepository;
import com.abo2.recode.dto.attendance.AttendanceReqDto;
import com.abo2.recode.dto.attendance.AttendanceRespDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class AttendanceService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final StudyMemberRepository studyMemberRepository;
    private final StudyRoomRepository studyRoomRepository;
    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;

    public AttendanceRespDto.markAttendanceRespDto markAttendance(String status, Long userId, Long studyId, AttendanceReqDto.markAttendanceReqDto markAttendanceReqDto) {

        StudyMember studyMember = studyMemberRepository.findByUserAndStudyRoom(userId, studyId)
                .orElseThrow(() -> new EntityNotFoundException("해당 Id 가 없습니다." + userId + studyId));

        StudyRoom studyRoom = studyRoomRepository.findById(markAttendanceReqDto.getStudyId())
                .orElseThrow(() -> new EntityNotFoundException("해당 스터디룸이 없습니다."));

        User user = userRepository.findById(markAttendanceReqDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 없습니다."));

        // 해당 날짜에 이미 출석이 있는지 확인
        boolean attendanceExists = attendanceRepository.existsByStudyRoomIdAndUserId(
                userId, studyId);

        if (!attendanceExists) {

            // 현재 요일과 스터디룸의 스터디 요일 비교
            DayOfWeek currentDayOfWeek = LocalDate.now().getDayOfWeek(); // 현재 요일
            String currentDay = null;

            switch (currentDayOfWeek) {
                case MONDAY:
                    currentDay = "월요일";
                    break;
                case TUESDAY:
                    currentDay = "화요일";
                    break;
                case WEDNESDAY:
                    currentDay = "수요일";
                    break;
                case THURSDAY:
                    currentDay = "목요일";
                    break;
                case FRIDAY:
                    currentDay = "금요일";
                    break;
                case SATURDAY:
                    currentDay = "토요일";
                    break;
                case SUNDAY:
                    currentDay = "일요일";
                    break;
            }

            Set<AttendanceDay> attendanceDays = studyRoom.getAttendanceDay(); // 스터디룸의 스터디 요일

            if (!attendanceDays.stream().map(AttendanceDay::getAttendanceDay).collect(Collectors.toSet()).contains(currentDay)) { // 스터디가 없는 날인 경우
                throw new IllegalArgumentException("오늘은 스터디가 없는 날입니다.");
            }

            // 현재 시간과 스터디룸의 출석 인정 시간 비교
            LocalTime now = LocalTime.now(); // 현재 시간
            LocalTime startTime = studyRoom.getStartTime(); // 스터디룸의 출석 인정 시작 시간
            LocalTime endTime = studyRoom.getEndTime(); // 스터디룸의 출석 인정 종료 시간

            Attendance attendance = new Attendance();
            attendance.setStudyRoom(studyRoom);
            attendance.setUser(user);
            attendance.setAttendanceDate(LocalDateTime.now());

            if (now.isAfter(startTime) && now.isBefore(endTime)) { // 현재 시간이 출석 인정 시간 내에 있는지 확인
                // 출석 표시
                attendance.setStatus(1); // 출석을 나타내는 값으로 1
            } else if (now.isAfter(endTime)) { // 현재 시간이 종료 시간 이후인 경우
                // 지각 표시
                attendance.setStatus(2); // 지각 나타내는 값으로 2
            } else {
                throw new IllegalArgumentException("현재 시간은 출석 인정 시간이 아닙니다.");
            }

            Attendance savedAttendance = attendanceRepository.save(attendance);
            return new AttendanceRespDto.markAttendanceRespDto(savedAttendance);

        } else {
            // 중복 출석 로그
            logger.warn("User {} tried to mark attendance on {} for study room {} but attendance already exists.", userId, studyId);
            throw new IllegalStateException("이미 출석되었습니다: " + userId + ", " + studyId);
        }
    }
}