package com.licenta.idm.business.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.licenta.idm.business.service.UserService;
import com.licenta.idm.persistence.entity.Role;
import com.licenta.idm.persistence.entity.User;
import com.licenta.idm.persistence.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {
    private static final String INITIAL_DATA_PATH = "src/main/resources/load/";

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    public void run(ApplicationArguments args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        if(roleRepository.findAll().size() == 0) {
            List<Role> roles = mapper.readValue(Paths.get(INITIAL_DATA_PATH + "roles.json").toFile(),
                    new TypeReference<List<Role>>() {
                    });
            roleRepository.saveAllAndFlush(roles);

            List<User> admins = mapper.readValue(Paths.get(INITIAL_DATA_PATH + "admins.json").toFile(),
                    new TypeReference<List<User>>() {
                    });
            for (User admin: admins) {
                userService.createAdmin(admin);
            }
        }
    }
}