package com.utsav.dockerspringboot.commandlinerunner;

import com.utsav.dockerspringboot.enums.Role;
import com.utsav.dockerspringboot.model.User;
import com.utsav.dockerspringboot.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setEmail("admin@gmail.com");
        admin.setRole(Role.ADMIN);

        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setRole(Role.USER);
        user.setEmail("user@gmail.com");
        user.initializeRewardPoints();



        return args -> {
            userRepository.save(admin);
            userRepository.save(user);
        };

    }
}