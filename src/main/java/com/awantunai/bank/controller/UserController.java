package com.awantunai.bank.controller;

import com.awantunai.bank.helper.ResourceNotFoundException;
import com.awantunai.bank.model.User;
import com.awantunai.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/awantunai")
public class UserController {

    @Autowired
    UserRepository userRepository;

    // Get All Users
    @GetMapping("/users")
    public List<User> getAllUser() {
      return userRepository.findAll();
    }

    // Create a User
    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
      return userRepository.save(user);
    }

    // Get a User
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable(value="id") Long userId) {
      return userRepository.findById(userId)
          .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    // Modify User
    @PutMapping("/users/deposit/{id}")
    public User editUser(@PathVariable(value="id") Long userId,
        @Valid @RequestBody User userDetails) {
      User user = userRepository.findById(userId)
          .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
      user.setFirstName(userDetails.getFirstName());
      user.setLastName(userDetails.getLastName());
      user.setPhone(userDetails.getPhone());
      user.setAddress(userDetails.getAddress());
      user.setBirthDate(userDetails.getBirthDate());
      User updateUser = userRepository.save(user);
      return updateUser;
    }


    // Delete a User
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value="id") Long userId) {
      User user = userRepository.findById(userId)
          .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

      userRepository.delete(user);

      return ResponseEntity.ok().build();
    }
}
