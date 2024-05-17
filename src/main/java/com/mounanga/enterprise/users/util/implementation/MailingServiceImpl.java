package com.mounanga.enterprise.users.util.implementation;

import com.mounanga.enterprise.users.execption.MailingException;
import com.mounanga.enterprise.users.util.ApplicationProperties;
import com.mounanga.enterprise.users.util.MailingService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
public class MailingServiceImpl implements MailingService {

    private static final Logger log = LoggerFactory.getLogger(MailingServiceImpl.class);
    private static final String VALIDATION_TEMPLATE = "validation.html";
    private static final String VALIDATION = "VALIDATION";

    private static final String NOTIFICATION_TEMPLATE = "notification.html";
    private static final String NOTIFICATION = "NOTIFICATION";

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final ApplicationProperties applicationProperties;

    public MailingServiceImpl(JavaMailSender mailSender, SpringTemplateEngine templateEngine, ApplicationProperties applicationProperties) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.applicationProperties = applicationProperties;
    }

    private void sendCodeByEmail(String to, String activationCode) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_MIXED, UTF_8.name());
        Map<String, Object> properties = new HashMap<>();
        properties.put("activation_code", activationCode);
        send(to, mimeMessage, helper, properties, VALIDATION, VALIDATION_TEMPLATE);
    }

    private @NotNull String getFormattedDate(@NotNull LocalDateTime when){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH'h'mm'mm'ss's'");
        return when.format(formatter);
    }

    private void sendNotificationByEmail(String to, String fullName, @NotNull LocalDateTime when, String where) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_MIXED, UTF_8.name());
        Map<String, Object> properties = new HashMap<>();
        properties.put("fullName", fullName);
        properties.put("when", getFormattedDate(when));
        properties.put("where", where);
        send(to, mimeMessage, helper, properties, NOTIFICATION, NOTIFICATION_TEMPLATE);
    }

    private void send(String to, MimeMessage mimeMessage, @NotNull MimeMessageHelper helper, Map<String, Object> properties, String subject, String templateGiven) throws MessagingException {
        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom(applicationProperties.getEmailSystem());
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(templateGiven, context);
        helper.setText(template, true);
        mailSender.send(mimeMessage);
    }

    @Async
    @Override
    public void sendVerificationCode(String email, String code) {
        log.info("Sending verification code to email: {}", email);
        try{
            sendCodeByEmail(email, code);
            log.info("Verification code sent");
        }catch(Exception e){
            throw new MailingException("Error sending verification's code to email: " + email+" due to "+e.getMessage());
        }
    }

    @Async
    @Override
    public void sendLoginNotification(String email, String fullName, LocalDateTime when, String where) {
        log.info("Sending login notification to email: {}", email);
        try{
            sendNotificationByEmail(email, fullName, when, where);
            log.info("Login notification sent");
        }catch (Exception e){
            throw new MailingException("Error sending notification to email: " + email+" due to "+e.getMessage());
        }
    }


}
