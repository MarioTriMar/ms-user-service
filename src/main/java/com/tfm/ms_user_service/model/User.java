package com.tfm.ms_user_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(value = "userEntity")
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;

    public void setPwd(String pwd) {
        this.password = org.apache.commons.codec.digest.DigestUtils.sha512Hex(pwd);
    }
}
