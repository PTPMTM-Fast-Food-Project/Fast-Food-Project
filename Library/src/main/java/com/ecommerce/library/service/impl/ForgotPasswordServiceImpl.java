package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.ForgotPasswordToken;
import com.ecommerce.library.repository.ForgotPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ecommerce.library.service.ForgotPasswordService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@Service
@Slf4j
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final int MINUTES = 10;
    private final String EMAIL_SERVER = "hoangkhang16112003@gmail.com";

    @Autowired
    private JavaMailSender javaMailSender;

    private final ForgotPasswordRepository forgotPasswordRepository;

    public ForgotPasswordServiceImpl(ForgotPasswordRepository forgotPasswordRepository) {
        this.forgotPasswordRepository = forgotPasswordRepository;
    }

    @Override
    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public LocalDateTime expireDateTimeRange() {
        return LocalDateTime.now().plusMinutes(MINUTES);
    }
    
    @Override
    public void sendEmail(String to, String subject, String emailLink) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String emailContent = "<p>Hello</p>"
                            + "Click the link below to reset your password "
                            + "<p> <a href=\"" + emailLink + "\">Reset Password</a> </p>";

        helper.setText(emailContent, true);
        helper.setFrom(EMAIL_SERVER, "My Spring Website");
        helper.setSubject(subject);
        helper.setTo(to);
        javaMailSender.send(message);        
    }

    @Override
    public ForgotPasswordToken createForgotPasswordToken(ForgotPasswordToken forgotPasswordToken) {
        return forgotPasswordRepository.save(forgotPasswordToken);
    }

    @Override
    public ForgotPasswordToken getForgotPasswordTokenByToken(String token) {
        return forgotPasswordRepository.findByToken(token);
    }

    @Override
    public boolean isExpired(ForgotPasswordToken forgotPasswordToken) {
        return LocalDateTime.now().isAfter(forgotPasswordToken.getExpireDateTime());
    }

    @Override
    public String checkValidity(ForgotPasswordToken forgotPasswordToken, Model model) {
        String page = "";
        model.addAttribute("title", "Error Page");
        model.addAttribute("page", "Error Page");
        if (forgotPasswordToken == null) {
            model.addAttribute("error", "Invalid token");
            page = "error-page";
        } else if (forgotPasswordToken.isUsed()) {
            model.addAttribute("error", "The token has been used");
            page = "error-page";
        } else if (isExpired(forgotPasswordToken)) {
            model.addAttribute("error", "The token has expired");
            page = "error-page";
        } else {
            page = "reset-password";
        }
        return page;
    }
}
