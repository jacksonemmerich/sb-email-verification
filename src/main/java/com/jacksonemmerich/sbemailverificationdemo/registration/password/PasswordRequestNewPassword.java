package com.jacksonemmerich.sbemailverificationdemo.registration.password;

import lombok.Data;



@Data
public class PasswordRequestNewPassword {
    private String email;
    private String oldPassword;
    private String newPassword;

}

