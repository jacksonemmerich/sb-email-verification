package com.jacksonemmerich.sbemailverificationdemo.registration.password;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class PasswordRequestUtil {
    private String email;
    private String oldPassword;
    private String newPassword;

}

