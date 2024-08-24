package com.ecommerce.library.service;

import java.io.UnsupportedEncodingException;

import com.ecommerce.library.model.ForgotPasswordToken;
import jakarta.mail.MessagingException;

import java.time.LocalDateTime;

import org.springframework.ui.Model;

public interface ForgotPasswordService {
    
    String generateToken();

    LocalDateTime expireDateTimeRange();

    void sendEmail(String to, String subject, String emailLink) throws UnsupportedEncodingException, MessagingException;

    ForgotPasswordToken createForgotPasswordToken(ForgotPasswordToken forgotPasswordToken);

    ForgotPasswordToken getForgotPasswordTokenByToken(String token);

    boolean isExpired(ForgotPasswordToken forgotPasswordToken);

    String checkValidity(ForgotPasswordToken forgotPasswordToken, Model model);
}
