package com.utsav.dockerspringboot.service.user;

import com.utsav.dockerspringboot.model.User;

public interface UserService {
    User save(User user);
    User findByUsername(String username);


}
