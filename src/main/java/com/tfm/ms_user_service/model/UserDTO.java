package com.tfm.ms_user_service.model;

import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String email;
    private String password1;
    private String password2;
    private String phone;
    private String address;
}
