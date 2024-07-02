package com.yutsuki.serverApi.email;

import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.exception.EmailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
public class SedMailService {
    @Value("${spring.mail.username}")
    private String from;
    @Resource
    private JavaMailSender mailSender;

    public void send(String to, String subject, String html) {
        MimeMessagePreparator message = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(from + "@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
        };
        mailSender.send(message);
    }

    // example of a method that sends an email
    public void sendEmil(String email, String name) throws BaseException {
        String html;
        try {
            html = readHtml("welcome.html");
        } catch (IOException e) {
            log.error("Email template not found", e);
            throw EmailException.templateNotFound();
        }
        String subject = "Welcome to Yutsuki";
        html = html.replace("{{name}}", name);

        send(email, subject, html);
    }

    public String readHtml(String filename) throws IOException {
        File file = ResourceUtils.getFile("classpath:email/" + filename);
        return FileCopyUtils.copyToString(new FileReader(file));
    }

}
