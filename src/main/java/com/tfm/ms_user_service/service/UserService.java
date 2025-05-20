package com.tfm.ms_user_service.service;

import com.tfm.ms_user_service.model.User;
import com.tfm.ms_user_service.model.UserDTO;
import com.tfm.ms_user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity register(UserDTO userDTO) {
        Optional<User> optUser = userRepository.findByEmail(userDTO.getEmail());
        if (optUser.isPresent()) {
            return new ResponseEntity<>("email already used", HttpStatus.FORBIDDEN);
        }
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPwd(userDTO.getPassword1());
        user.setAddress(userDTO.getAddress());
        user.setPhone(userDTO.getPhone());
        userRepository.save(user);
        return new ResponseEntity<>("User created", HttpStatus.CREATED);
    }

    public boolean getUser(String userId) {
        Optional<User> optUser = userRepository.findById(userId);
        if(optUser.isPresent()){
            return true;
        }else{
            return false;
        }
    }
}
