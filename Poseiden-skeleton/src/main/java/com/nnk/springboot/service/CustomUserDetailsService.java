package com.nnk.springboot.service;

import com.nnk.springboot.repositories.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import java.util.*;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.nnk.springboot.domain.User> optional = userRepository.findByUsername(username);
        com.nnk.springboot.domain.User currentUser = null;
        if (optional.isPresent()){
            currentUser = optional.get();
            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(currentUser.getUsername())
                    .password(currentUser.getPassword())
                    .roles(currentUser.getRole())
//                    .authorities(getAuthorities(currentUser))
                    .build();
            return userDetails;
        }
        else {
            throw new UsernameNotFoundException(username);
        }
    }
}
