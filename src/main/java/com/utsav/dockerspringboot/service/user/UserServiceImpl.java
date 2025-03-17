package com.utsav.dockerspringboot.service.user;

import com.utsav.dockerspringboot.enums.Role;
import com.utsav.dockerspringboot.model.User;
import com.utsav.dockerspringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User save(User user) {
       if(userRepository.existsByUsername(user.getUsername())){
           throw new RuntimeException("Username already exists");
        }
       if(user.getRole()==null){
           user.setRole(Role.USER);
       }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }
}
