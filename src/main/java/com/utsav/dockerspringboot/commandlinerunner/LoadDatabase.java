//package com.utsav.dockerspringboot.commandlinerunner;
//
//import com.utsav.dockerspringboot.enums.Role;
//import com.utsav.dockerspringboot.model.User;
//import com.utsav.dockerspringboot.repository.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//public class LoadDatabase {
//
//    @Bean
//    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        return args -> {
//            userRepository.save(new User(1l, "admin", passwordEncoder.encode("admin123"), "utsav","mahato",Role.ADMIN));
//            userRepository.save(new User(2l, "user", passwordEncoder.encode("user123"), "utsav","mahato",Role.USER));
//        };
//    }
//}