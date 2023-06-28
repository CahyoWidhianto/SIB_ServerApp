package id.co.mii.serverapp.models.dto.requests;

import lombok.Data;

@Data
public class EmailRequest {

  private String to;
  private String subject;
  private String text;
  private String attach;
}