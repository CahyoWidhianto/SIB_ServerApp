package id.co.mii.serverapp.controller;

import id.co.mii.serverapp.models.dto.requests.EmailRequest;
import id.co.mii.serverapp.models.dto.requests.mailTemplate.EmailRequestsTemplate;
import id.co.mii.serverapp.models.dto.response.MailResponse;
import id.co.mii.serverapp.services.EmailService;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@AllArgsConstructor
public class EmailController {

  private EmailService emailService;

  @PostMapping("/simple")
  public EmailRequest sendSimpleMessage(@RequestBody EmailRequest emailRequest) {
    return emailService.sendSimpleMessage(emailRequest);
  }

  @PostMapping("/attach")
  public EmailRequest sendMessageWithAttachment(@RequestBody EmailRequest emailRequest) {
    return emailService.sendMessageWithAttachment(emailRequest);
  }

  @PostMapping("/Template")
  public MailResponse sendEmailWithTemplate(@RequestBody EmailRequestsTemplate request){
    Map<String, Object> model = new HashMap<>();
    model.put("Name", request.getName());
    model.put("Subject", request.getSubject());
    // model.put("Location", "D.I Yogyakarta, Indonesia");
    return emailService.sendEmailWithTemplate(request, model);
  }
}