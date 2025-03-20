package com.utsav.dockerspringboot.repository;

import com.utsav.dockerspringboot.model.Cart;
import com.utsav.dockerspringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    // Custom query methods can be defined here if needed

    List<Cart> findByUserUserId(Long userId);
    Cart findByUserUserIdAndProductProductId(Long userId, Long product_Id);
    List<Cart> findByUser(User user);
}