package com.tfm.ms_user_service.controller;

import com.tfm.ms_user_service.model.UserDTO;
import com.tfm.ms_user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RequestMapping("/user")
@RestController()
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity register(@RequestBody UserDTO userDTO) {
        String pwd1 = userDTO.getPassword1();
        String pwd2 = userDTO.getPassword2();
        if (!pwd1.equals(pwd2))
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Las contraseñas no coinciden");

        try {
            return this.userService.register(userDTO);
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public boolean getUser(@PathVariable String id){
        return userService.getUser(id);
    }
}
