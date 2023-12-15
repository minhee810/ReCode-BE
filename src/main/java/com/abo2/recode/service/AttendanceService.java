package com.abo2.recode.service;

import com.abo2.recode.domain.attendance.Attendance;
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
import java.time.LocalDateTime;
import java.time.LocalTime;

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

            // 현재 시간과 스터디룸의 출석 인정 시간 비교
            LocalTime now = LocalTime.now(); // 현재 시간
            LocalTime startTime = studyRoom.getStartTime(); // 스터디룸의 출석 인정 시작 시간
            LocalTime endTime = studyRoom.getEndTime(); // 스터디룸의 출석 인정 종료 시간

            if (!(now.isAfter(startTime) && now.isBefore(endTime))) { // 현재 시간이 출석 인정 시간 내에 있는지 확인
                throw new IllegalArgumentException("현재 시간은 출석 인정 시간이 아닙니다.");
            }

            if (status.equals("Checked")) {
                //attendance 테이블 업데이트(상태값 0 -> 1)
                attendanceRepository.markAttendance(1, studyId, userId);

                // 출석 표시
                Attendance attendance = new Attendance();
                attendance.setStudyRoom(studyRoom);
                attendance.setUser(user);
                attendance.setAttendanceDate(LocalDateTime.now());
                attendance.setStatus(1); // 출석을 나타내는 값으로 1

                Attendance savedAttendance = attendanceRepository.save(attendance);
                return new AttendanceRespDto.markAttendanceRespDto(savedAttendance);

            } else if (status.equals("Unchecked")) {
                //attendance 테이블 업데이트(상태값 0)
                attendanceRepository.markAttendance(0, studyId, userId);

                // 출석 표시
                Attendance attendance = new Attendance();
                attendance.setStudyRoom(studyRoom);
                attendance.setUser(user);
                attendance.setAttendanceDate(LocalDateTime.now());
                attendance.setStatus(0); // 미출석을 나타내는 값으로 0

                Attendance savedAttendance = attendanceRepository.save(attendance);
                return new AttendanceRespDto.markAttendanceRespDto(savedAttendance);

            } else {
                // 잘못된 출석 상태 로그
                logger.warn("User {} tried to mark attendance with invalid status {} for study room {}.", userId, status, studyId);
                throw new IllegalArgumentException("잘못된 출석 상태: " + status);
            }

        } else {
            // 중복 출석 로그
            logger.warn("User {} tried to mark attendance on {} for study room {} but attendance already exists.", userId, studyId);
            throw new IllegalStateException("이미 출석이 표시되었습니다: " + userId + ", " + studyId);
        }
    }
}
