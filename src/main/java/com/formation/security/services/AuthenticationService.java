package com.formation.security.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.formation.security.dtos.LoginUserDto;
import com.formation.security.dtos.RegisterUserDto;
import com.formation.security.entities.Role;
import com.formation.security.entities.RoleE;
import com.formation.security.entities.User;
import com.formation.security.repositories.RoleRepository;
import com.formation.security.repositories.UserRepository;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RoleRepository roleRepository;

    public User signup(RegisterUserDto input) {
        Optional<Role> role = roleRepository.findByName(RoleE.USER);

        if(role.isEmpty()){
            return null;
        }

        var user = new User()
            .setFullName(input.getFullName())
            .setEmail(input.getEmail())
            .setPassword(passwordEncoder.encode(input.getPassword()))
            .setRole(role.get());

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()));

        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
}
