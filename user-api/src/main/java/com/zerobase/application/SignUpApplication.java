package com.zerobase.application;

import com.zerobase.client.MailgunClient;
import com.zerobase.client.mailgun.SendMailForm;
import com.zerobase.client.service.SignUpCustomerService;
import com.zerobase.domain.SignUpForm;
import com.zerobase.domain.model.Customer;
import com.zerobase.exception.CustomException;
import com.zerobase.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignUpApplication {
    private final MailgunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;

    public void customerVerify(String email, String code) {
        signUpCustomerService.verifyEmail(email, code);
    }

    public String customerSignUp (SignUpForm form) {
        if (signUpCustomerService.isEmailExist(form.getEmail())) {
            // exception
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            Customer c = signUpCustomerService.signUp(form);
            LocalDateTime now = LocalDateTime.now();

            String code =getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                                    .from("tester@dannymytester.com")
                                    .to(form.getEmail())
                                    .subject("Verification Email!")
                                    .text(getVerificationEmailBody(form.getEmail(), form.getName(), code))
                                    .build();
            log.info("Send email result : "+ mailgunClient.sendEmail(sendMailForm).getBody());

            mailgunClient.sendEmail(sendMailForm);
            signUpCustomerService.changeCustomerValidateEmail(c.getId(), code);
            return "회원 가입에 성공하였습니다.";
        }
    }

    private String getRandomCode() {return RandomStringUtils.random(10, true, true);}
    private String getVerificationEmailBody(String email, String name, String code) {
        StringBuilder builder = new StringBuilder();
        return builder.append("Hello ").append(name).append("! Please Click Link for verification.\n\n")
                .append("http://localhost:8080/signup/verify/customer?email=")
                .append(email)
                .append("&code=")
                .append(code).toString();

    }
}
