package id.co.mii.serverapp.models.dto.requests;

import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String email;
    private String phone;
    private String username;
    private String password;

}
