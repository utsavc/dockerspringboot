package com.utsav.dockerspringboot.service;
import com.utsav.dockerspringboot.model.User;
import com.utsav.dockerspringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (byUsername == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        return new MyUserPrincipal(byUsername.get());
    }
}