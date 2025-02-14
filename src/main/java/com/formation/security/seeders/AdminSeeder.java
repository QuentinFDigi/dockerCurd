package com.formation.security.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.formation.security.dtos.RegisterUserDto;
import com.formation.security.entities.Role;
import com.formation.security.entities.RoleE;
import com.formation.security.entities.User;
import com.formation.security.repositories.RoleRepository;
import com.formation.security.repositories.UserRepository;

import java.util.Optional;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdministrator();
    }

    private void createSuperAdministrator() {
        RegisterUserDto userDto = new RegisterUserDto();
        userDto.setFullName("Super Admin").setEmail("super.admin@email.com").setPassword("123456");

        Optional<Role> optionalRole = roleRepository.findByName(RoleE.SUPER_ADMIN);
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        var user = new User()
                .setFullName(userDto.getFullName())
                .setEmail(userDto.getEmail())
                .setPassword(passwordEncoder.encode(userDto.getPassword()))
                .setRole(optionalRole.get());

        userRepository.save(user);
    }
}
