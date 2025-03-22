package com.utsav.dockerspringboot.service.user;

import com.utsav.dockerspringboot.enums.Role;
import com.utsav.dockerspringboot.exception.DuplicateUsernameException;
import com.utsav.dockerspringboot.model.RewardPoints;
import com.utsav.dockerspringboot.model.User;
import com.utsav.dockerspringboot.repository.RewardPointsRepository;
import com.utsav.dockerspringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RewardPointsRepository rewardPointsRepository;


    @Override
    public User save(User user) {
       if(userRepository.existsByUsername(user.getUsername())){
           throw new DuplicateUsernameException("Username already exists");
        }
       if(user.getRole()==null){
           user.setRole(Role.USER);
       }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.initializeRewardPoints();

        User saved = userRepository.save(user);

        return saved;
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }
}
