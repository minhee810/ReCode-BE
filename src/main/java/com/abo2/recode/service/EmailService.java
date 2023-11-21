package com.abo2.recode.service;

import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@RequiredArgsConstructor
@Service
public class EmailService {

    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    public void sendConfirmEmail(User user) throws MessagingException {
        String url = "http://localhost:3000/changePassword";

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

        // Create a MimeMessage using JavaMailSender
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setText(msgg, true); // true indicates HTML content
        helper.setTo(user.getEmail());
        helper.setSubject("Recode 비밀번호 변경 관련 인증 코드");
        helper.setFrom("Recode");

        javaMailSender.send(mimeMessage);
    }
}