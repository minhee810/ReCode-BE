package com.abo2.recode.service;

import com.abo2.recode.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendConfirmEmail(User user) throws MessagingException {
        String url = "http://52.79.108.89:8080/changePassword?token=" + user.getEmailCheckToken() + "&email=" + user.getEmail();

        String msgg = "<div style='margin:20px;'>";
        msgg += "<h1> 안녕하세요 Recode 입니다. </h1>";
        msgg += "<br>";
        msgg += "<p>아래의 주소로 접속해 진행을 완료해주세요<p>";
        msgg += "<br>";
        msgg += "<p>감사합니다.<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana;'>";
        msgg += "<h3 style='color:blue;'>회원가입 인증 페이지입니다</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "<a href='" + url + "'>링크를 통해 비밀번호 변경을 진행해주세요.</a></div><br/> ";
        msgg += "</div>";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setText(msgg, true); // true indicates HTML content
        helper.setTo(user.getEmail());
        helper.setSubject("Recode 스터디 신청에 관한 알림입니다.");
        helper.setFrom("Recode");

        javaMailSender.send(mimeMessage);
    }

    public void sendApprovedEmail(Long studyId, Long userId, User user) throws MessagingException {
        String url = "http://52.79.108.89:8080/login";

        String msgg = "<div style='margin:20px;'>";
        msgg += "<h1> 안녕하세요 Recode 입니다. </h1>";
        msgg += "<br>";
        msgg += "<p>신청하신 스터디에 가입이 승인되셨습니다.<p>";
        msgg += "<br>";
        msgg += "<p>Recode 에 접속하여 확인해주세요!<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana;'>";
        msgg += "<h3 style='color:blue;'>Recode 로그인하러 가기</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "<a href='" + url + "'>링크를 눌러 승인된 스터디를 확인하세요!</a></div><br/> ";
        msgg += "</div>";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setText(msgg, true); // true indicates HTML content
        helper.setTo(user.getEmail());
        helper.setSubject("Recode 스터디 신청에 관한 알림입니다.");
        helper.setFrom("Recode");

        javaMailSender.send(mimeMessage);
    }

    public void sendRejectedEmail(Long studyId, Long userId, User user) throws MessagingException {
        String url = "http://52.79.108.89:8080/login";

        String msgg = "<div style='margin:20px;'>";
        msgg += "<h1> 안녕하세요 Recode 입니다. </h1>";
        msgg += "<br>";
        msgg += "<p>신청하신 스터디에 가입이 거절되셨습니다.<p>";
        msgg += "<br>";
        msgg += "<p>Recode 에 접속하여 확인해주세요!<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana;'>";
        msgg += "<h3 style='color:blue;'>Recode 로그인하러 가기</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "<a href='" + url + "'>링크를 눌러 다른 스터디도 둘러봐 주세요!</a></div><br/> ";
        msgg += "</div>";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setText(msgg, true); // true indicates HTML content
        helper.setTo(user.getEmail());
        helper.setSubject("Recode 비밀번호 변경 관련 인증 코드");
        helper.setFrom("Recode");

        javaMailSender.send(mimeMessage);
    }
}
