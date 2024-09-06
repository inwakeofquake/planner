package com.planner.planner.service;

import com.planner.planner.entity.User;
import com.planner.planner.exception.DuplicateResourceException;
import com.planner.planner.exception.ResourceNotFoundException;
import com.planner.planner.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        logger.info("Fetching user with id: {}", id);
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        logger.info("Fetching user with email: {}", email);
        return userRepository.findByEmail(email);
    }

    public User createUser(User user) {
        logger.info("Creating new user: {}", user);
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.warn("Attempt to create user with existing email: {}", user.getEmail());
            throw new DuplicateResourceException("Email already exists: " + user.getEmail());
        }
        User savedUser = userRepository.save(user);
        logger.info("Created new user with id: {}", savedUser.getId());
        return savedUser;
    }

    public User updateUser(String id, User userDetails) {
        logger.info("Updating user with id: {}", id);
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(userDetails.getName());
                    if (!existingUser.getEmail().equals(userDetails.getEmail()) && userRepository.existsByEmail(userDetails.getEmail())) {
                        logger.warn("Attempt to update user with existing email: {}", userDetails.getEmail());
                        throw new DuplicateResourceException("Email already exists: " + userDetails.getEmail());
                    }
                    existingUser.setEmail(userDetails.getEmail());
                    existingUser.setOrigin(userDetails.getOrigin());
                    User updatedUser = userRepository.save(existingUser);
                    logger.info("Updated user with id: {}", updatedUser.getId());
                    return updatedUser;
                })
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public void deleteUser(String id) {
        logger.info("Deleting user with id: {}", id);
        userRepository.deleteById(id);
        logger.info("Deleted user with id: {}", id);
    }

    public boolean existsByEmail(String email) {
        logger.info("Checking if user exists with email: {}", email);
        return userRepository.existsByEmail(email);
    }
}