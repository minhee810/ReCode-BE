package com.abo2.recode.service;

import com.abo2.recode.domain.attendance.Attendance;
import com.abo2.recode.domain.attendance.AttendanceRepository;
import com.abo2.recode.domain.studymember.StudyMember;
import com.abo2.recode.domain.studymember.StudyMemberRepository;
import com.abo2.recode.dto.attendance.AttendanceRespDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AttendanceService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final StudyMemberRepository studyMemberRepository;
    private final AttendanceRepository attendanceRepository;

    public AttendanceRespDto markAttendance(Long userId, Long studyId, LocalDateTime attendanceDate) {

        StudyMember studyMember = studyMemberRepository.findByUserAndStudyRoom(userId, studyId)
                .orElseThrow(() -> new EntityNotFoundException("해당 Id 가 없습니다." + userId + studyId));

        if (studyMember != null) {
            // 해당 날짜에 이미 출석이 있는지 확인
            boolean attendanceExists = attendanceRepository.existsByStudyRoomIdAndUserIdAndAttendanceDate(
                    userId, studyId, attendanceDate);

            if (!attendanceExists) {
                // 출석 표시
                Attendance attendance = new Attendance();
                attendance.setStudyRoom(studyMember.getStudyRoom());
                attendance.setUser(studyMember.getUser());
                attendance.setAttendanceDate(attendanceDate);
                attendance.setStatus(1); // 출석을 나타내는 값으로 1

                attendanceRepository.save(attendance);

            } else {

                // 중복 출석 로그
                logger.warn("User {} tried to mark attendance on {} for study room {} but attendance already exists.", userId, attendanceDate, studyId);
            }

        } else {
            logger.error("Study member not found for user {} in study room {}.", userId, studyId);
            throw new EntityNotFoundException("해당 유저는 해당 스터디에 속해있지 않습니다.");
        }

        return new AttendanceRespDto();
    }
}
