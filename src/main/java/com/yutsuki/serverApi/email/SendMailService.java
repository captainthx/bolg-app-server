package com.yutsuki.serverApi.email;

import com.yutsuki.serverApi.common.EmailProperties;
import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.exception.EmailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
@Slf4j
public class SendMailService {
    @Resource
    private MailProperties mailProperties;
    @Resource
    private JavaMailSender mailSender;

    @Resource
    private EmailProperties emailProperties;

    public void send(String to, String subject, String html) {
        MimeMessagePreparator message = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(mailProperties.getUsername());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
        };
        mailSender.send(message);
    }

    public void sendResetPassword(String email, String link) throws BaseException {
        String html;
        try {
            html = readResetEmail();
        } catch (IOException e) {
            log.error("Email template not found {}", e.getMessage());
            throw EmailException.templateNotFound();
        }
        String subject = "Reset password request";
        html = html.replace("{$link}", link);
        try {
            send(email, subject, html);
        } catch (Exception e) {
            log.error("Error sending email {}", e.getMessage());
            throw EmailException.sendFailed(e.getMessage());
        }
    }

    public String readResetEmail() throws IOException {
        File file = ResourceUtils.getFile(emailProperties.getResetPasswordForm());
        return FileCopyUtils.copyToString(new FileReader(file));
    }

}
