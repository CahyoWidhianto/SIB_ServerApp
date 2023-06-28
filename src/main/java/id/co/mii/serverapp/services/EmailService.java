package id.co.mii.serverapp.services;

import java.io.File;
// import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.internet.MimeMessage;
// import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import id.co.mii.serverapp.models.dto.requests.EmailRequest;
import id.co.mii.serverapp.models.dto.requests.mailTemplate.EmailRequestsTemplate;
import id.co.mii.serverapp.models.dto.response.MailResponse;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration config;

    public EmailRequest sendSimpleMessage(EmailRequest emailRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailRequest.getTo());
        message.setSubject(emailRequest.getSubject());
        message.setText(emailRequest.getText());
        mailSender.send(message);
        return emailRequest;
    }

    public EmailRequest sendMessageWithAttachment(EmailRequest emailRequest) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(emailRequest.getTo());
            helper.setSubject(emailRequest.getSubject());
            helper.setText(emailRequest.getText());

            FileSystemResource file = new FileSystemResource(
                    new File(emailRequest.getAttach()));

            helper.addAttachment(file.getFilename(), file);
            mailSender.send(message);
        } catch (Exception e) {
            throw new IllegalStateException("Email false to send!!!");
        }
        return emailRequest;
    }


    public MailResponse sendEmailWithTemplate(EmailRequestsTemplate request, Map<String, Object> model){
        MailResponse response = new MailResponse();
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(
                message, 
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
                // helper.addAttachment("logo.png", new ClassPathResource("logo.png"));
                Template t = config.getTemplate("email-template.ftl");
                String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

                helper.setTo(request.getTo());
                helper.setText(html, true);
                helper.setSubject(request.getSubject());
                helper.setFrom(request.getFrom());
                mailSender.send(message);

                response.setMessage("mail send to : " + request.getTo());
                response.setStatus(Boolean.TRUE);
                
        } catch (Exception e) {
            response.setMessage("Mail sending failure : " + e.getMessage());
            response.setStatus(Boolean.FALSE);
        }
        return response;
    }
    
}
