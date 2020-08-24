package com.booktickets.controller;

import com.booktickets.exception.ResourceNotFoundException;
import com.booktickets.model.User;
import com.booktickets.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable(value = "id") Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
    }

    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable(value = "id") Long userId,
                                   @Valid @RequestBody User details) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setName(details.getName());

        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }
}
