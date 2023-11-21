package com.abo2.recode.service;

import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class EmailService {

    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    public void sendConfirmEmail(User user) {
        //이메일 전송
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Recode 비밀번호 변경 관련 인증 코드");
        mailMessage.setText("/check-email-token?token="+ user.getEmailCheckToken() +
                "&email=" + user.getEmail());
        javaMailSender.send(mailMessage);
    }
}
