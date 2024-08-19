package com.nnk.springboot.service;

import com.nnk.springboot.domain.*;
import com.nnk.springboot.domain.dto.*;
import com.nnk.springboot.repositories.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> displayAllUser() {
        return userRepository.findAll()
                .stream()
                .map(
                        user -> {
                            return new UserDTO(user.getId(),
                                    user.getUsername(),
                                    user.getFullname(),
                                    user.getPassword(),
                                    user.getRole());
                        })
                .collect(Collectors.toList());
    }

    public User addUser(UserDTO userDTO) {
        User user = new User();
        user.setFullname(userDTO.getFullname());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        return userRepository.save(user);
    }

    public UserDTO displayUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("could not find user by this id"));
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setFullname(user.getFullname());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public User updateUser(Integer id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("could not find user  by given id"));
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setFullname(userDTO.getFullname());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("could not find user by this id"));
        userRepository.delete(user);
    }
}
