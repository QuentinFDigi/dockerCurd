package com.formation.security.controllers;

import java.util.List;

import com.formation.security.dtos.RegisterUserDto;
import com.formation.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.formation.security.entities.User;
import com.formation.security.services.AuthenticationService;

@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    /*@GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }*/

    @GetMapping
    public List<User> allUsers(){
        return authService.allUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id){
        return userService.getUser(id);
    }

    @PostMapping("/add")
    public User addUser(@RequestBody RegisterUserDto userDto){
        return userService.createUser(userDto);
    }

    @PutMapping("/update/{id}")
    public User modifyUser(@PathVariable int id, @RequestBody RegisterUserDto userDto){
        return userService.modifyUser(id, userDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable int id){
        userService.deleteUser(id);
    }
}
