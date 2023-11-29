package com.abo2.recode.controller;

import com.abo2.recode.config.auth.LoginUser;
import com.abo2.recode.domain.user.User;
import com.abo2.recode.domain.user.UserRepository;
import com.abo2.recode.dto.ResponseDto;
import com.abo2.recode.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class EmailController {

    private final UserRepository userRepository;
    private final EmailService emailService;

    @GetMapping(value ="/email")
    public ResponseEntity<?> emailForm(){
        return new ResponseEntity<>(new ResponseDto<>(1, "회원 인증을 위한 이메일 입력 페이지입니다.", null), HttpStatus.OK);
    }

    @PostMapping(value = {"/v1/send-email", "/send-email"})
    public ResponseEntity<?> sendEmail(@AuthenticationPrincipal LoginUser loginUser,
                                       @RequestParam String email) throws MessagingException {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if(!userOpt.isPresent()) {
            return ResponseEntity.badRequest().body(new ResponseDto<>(-1, "해당 이메일이 존재하지 않습니다.", null));
        }

        User user = userOpt.get();
        user.generateEmailCheckToken();
        userRepository.save(user);

        emailService.sendConfirmEmail(user);

        return new ResponseEntity<>(new ResponseDto<>(1, "인증 이메일이 전송되었습니다.", null), HttpStatus.OK);
    }

    @GetMapping(value = {"/v1/check-mail-token", "/check-mail-token"})
    public ResponseEntity<?> checkEmailToken(@AuthenticationPrincipal LoginUser loginUser,
                                             @RequestParam String emailCheckToken, @RequestParam String email){
        Optional<User> userOpt = userRepository.findByEmail(email);
        Optional<User> userEm = userRepository.findByEmailCheckToken(emailCheckToken);

        if (!userOpt.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseDto<>(-1, "해당 이메일이 존재하지 않습니다.", null));
        }

        User user = userOpt.get();
        if (!user.getEmailCheckToken().equals(emailCheckToken)) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseDto<>(-1, "토큰이 유효하지 않습니다", null));
        }

        user.completeSignUp();
        userRepository.save(user);

        return ResponseEntity.ok(new ResponseDto<>(1, "이메일이 확인되었습니다.", user));
    }
}
