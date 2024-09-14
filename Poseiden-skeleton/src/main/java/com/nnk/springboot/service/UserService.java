package com.nnk.springboot.service;

import com.nnk.springboot.domain.*;
import com.nnk.springboot.domain.dto.*;
import com.nnk.springboot.repositories.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Find list user and Convert userDto
     * @return the list of userDto
     */
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

    /**
     * adding new user to database , convert userDTO to user
     * @param userDTO given info by user
     * @return accepted user save in DB
     */
    public User addUser(UserDTO userDTO) {
        User user = new User();
        user.setFullname(userDTO.getFullname());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        return userRepository.save(user);
    }

    /**
     * find user by via id , convert to userDTO
     * @param id id of user in DB
     * @return the  corresponding user  or issue userNotFoundException
     */
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

    /**
     * Updates an existing user object with the provided new values.
     * @param id the ID of the user object to update.
     * @param userDTO bid the user object containing the new values.
     * @return the updated user object saved to the database.
     */
    public User updateUser(Integer id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("could not find user  by given id"));
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setFullname(userDTO.getFullname());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        return userRepository.save(user);
    }

    /**
     * Deletes a user object by its ID.
     * @param id the ID of the user object to delete.
     */
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("could not find user by this id"));
        userRepository.delete(user);
    }
}
