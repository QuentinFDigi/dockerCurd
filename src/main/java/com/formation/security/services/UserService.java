package com.formation.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.formation.security.dtos.RegisterUserDto;
import com.formation.security.entities.Role;
import com.formation.security.entities.RoleE;
import com.formation.security.entities.User;
import com.formation.security.repositories.RoleRepository;
import com.formation.security.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createAdmin(RegisterUserDto input){
        Optional<Role> role = roleRepository.findByName(RoleE.ADMIN);

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

    public User getUser(int id){
        return userRepository.findById(id).orElseThrow();
    }

    public User createUser(RegisterUserDto userDto) {
        Optional<Role> role = roleRepository.findByName(RoleE.USER);

        if(role.isEmpty()){
            return null;
        }

        User user = new User()
                .setFullName(userDto.getFullName())
                .setEmail(userDto.getEmail())
                .setPassword(userDto.getPassword())
                .setRole(role.get());

        return userRepository.save(user);
    }

    public User modifyUser(int id, RegisterUserDto userDto) {
        User oldUser = userRepository.findById(id).orElseThrow();

        oldUser.setFullName(userDto.getFullName())
                .setEmail(userDto.getEmail())
                .setPassword(userDto.getPassword());

        return userRepository.save(oldUser);
    }

    public void deleteUser(int id) {
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
    }
}
