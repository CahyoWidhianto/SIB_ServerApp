package id.co.mii.serverapp.models.dto.requests.mailTemplate;

import lombok.Data;

@Data
public class EmailRequestsTemplate {
    private String name;
    private String to;
    private String from;
    private String subject;

}
