package com.formation.security.seeders;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.formation.security.entities.Role;
import com.formation.security.entities.RoleE;
import com.formation.security.repositories.RoleRepository;


@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent>{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    private void loadRoles() {
        RoleE[] roleNames = new RoleE[] { RoleE.USER, RoleE.ADMIN, RoleE.SUPER_ADMIN, RoleE.MANAGER };
        Map<RoleE, String> roleDescriptionMap = Map.of(
                RoleE.USER, "Default user role",
                RoleE.ADMIN, "Administrator role",
                RoleE.SUPER_ADMIN, "Super Administrator role",
                RoleE.MANAGER, "Manager role"
        );

        Arrays.stream(roleNames).forEach((roleName) -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);

            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Role roleToCreate = new Role();

                roleToCreate.setName(roleName)
                        .setDescription(roleDescriptionMap.get(roleName));

                roleRepository.save(roleToCreate);
            });
        });
    }

    
}
