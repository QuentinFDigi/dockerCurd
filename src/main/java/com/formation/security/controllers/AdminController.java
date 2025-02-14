package com.formation.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.formation.security.dtos.RegisterUserDto;
import com.formation.security.entities.User;
import com.formation.security.services.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
    

    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<User> createAdmin(@RequestBody RegisterUserDto registerUserDto){
        User admin = userService.createAdmin(registerUserDto);

        return ResponseEntity.ok(admin);
    }
    




}
