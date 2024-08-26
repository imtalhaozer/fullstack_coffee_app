package com.commerceapp.commerceapp.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.commerceapp.commerceapp.DTO.AuthRequest;
import com.commerceapp.commerceapp.DTO.UserDTO;
import com.commerceapp.commerceapp.entity.User;
import com.commerceapp.commerceapp.service.AuthenticationService;
import com.commerceapp.commerceapp.service.UserService;

@RestController
public class UserController {
    private UserService userService;
    private AuthenticationService authenticationService;
    
    public UserController(UserService userService,AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService=authenticationService;
    }

    @PostMapping("/user/save")
    public UserDTO saveNewUser(@RequestBody User user){
        return userService.saveNewUser(user);
    }

    @PostMapping("/generatetoken")
    public String generateToken(@RequestBody AuthRequest authrequest){
        return authenticationService.generateToken(authrequest);
    }

    @GetMapping("/user/get/{id}")
    public UserDTO getUserById(@PathVariable ("id") Long userId){
        return userService.getUserIdById(userId);
    }

    @GetMapping("/get/currentUserId")
    public Long getCurrentUserId(){
        return userService.getCurrentUserId();
    }

    @DeleteMapping("/user/delete/{id}")
    public void deleteUserById(@PathVariable ("id") Long userId){
        userService.deleteUserById(userId);
    }

    @PutMapping("/user/update/{id}")
    public UserDTO updateUserById(@RequestBody User user, @PathVariable ("id") Long userId){
        return userService.updateUserById(user,userId);
    }

}
