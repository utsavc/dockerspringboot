package com.utsav.dockerspringboot.service.cart;

import com.utsav.dockerspringboot.dto.CartDto;
import com.utsav.dockerspringboot.model.Cart;
import com.utsav.dockerspringboot.model.Product;
import com.utsav.dockerspringboot.model.User;
import com.utsav.dockerspringboot.repository.CartRepository;
import com.utsav.dockerspringboot.repository.ProductRepository;
import com.utsav.dockerspringboot.repository.UserRepository;
import com.utsav.dockerspringboot.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MyUtils myUtils;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ProductRepository productRepository;


    @Override
    public Cart addToCart(CartDto cartDto) {
        Product product= productRepository.findById(cartDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

            User user = userRepository.findByUsername(myUtils.getCurrentUsername())
                    .orElseThrow(()-> new UsernameNotFoundException("Unauthorized User"));

            Cart cart =new Cart();
            cart.setProduct(product);
            cart.setUser(user);
            cart.setQuantity(cartDto.getQuantity());
            cart.setAddedAt(cartDto.getAddedAt());
            cartRepository.save(cart);
            return cart;
    }




}