package com.awantunai.bank.controller;

import com.awantunai.bank.helper.ResourceNotFoundException;
import com.awantunai.bank.model.User;
import com.awantunai.bank.repository.UserRepository;
import com.awantunai.bank.controller.AdminController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/awantunai")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdminController adminController = new AdminController();

    // Get All Users
    @GetMapping("/users")
    public ResponseEntity<?> getAllUser(@RequestParam("sessionId") String sessionId) {
      // Check if Admin is logged in
      if (!adminController.findAdminBySessionId(sessionId)) {
        return ResponseEntity.badRequest().body("Please login first.");
      }
      return ResponseEntity.ok().body(userRepository.findAll());
    }

    // Create a User
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestParam("sessionId") String sessionId, @Valid @RequestBody User user) {
      // Check if Admin is logged in
      if (!adminController.findAdminBySessionId(sessionId)) {
        return ResponseEntity.badRequest().body("Please login first.");
      }
      return ResponseEntity.ok().body(userRepository.save(user));
    }

    // Get a User
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@RequestParam("sessionId") String sessionId, @PathVariable(value="id") Long userId) {
      // Check if Admin is logged in
      if (!adminController.findAdminBySessionId(sessionId)) {
        return ResponseEntity.badRequest().body("Please login first.");
      }
      return ResponseEntity.ok().body(userRepository.findById(userId)
          .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId)));
    }

    // Modify User
    @PutMapping("/users/{id}")
    public ResponseEntity<?> editUser(@RequestParam("sessionId") String sessionId, @PathVariable(value="id") Long userId,
        @Valid @RequestBody User userDetails) {
      // Check if Admin is logged in
      if (!adminController.findAdminBySessionId(sessionId)) {
        return ResponseEntity.badRequest().body("Please login first.");
      }
      User user = userRepository.findById(userId)
          .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
      user.setFirstName(userDetails.getFirstName());
      user.setLastName(userDetails.getLastName());
      user.setPhone(userDetails.getPhone());
      user.setAddress(userDetails.getAddress());
      user.setBirthDate(userDetails.getBirthDate());
      User updateUser = userRepository.save(user);
      return ResponseEntity.ok().body(updateUser);
    }

    // Delete a User
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@RequestParam("sessionId") String sessionId, @PathVariable(value="id") Long userId) {
      // Check if Admin is logged in
      if (!adminController.findAdminBySessionId(sessionId)) {
        return ResponseEntity.badRequest().body("Please login first.");
      }
      User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
      userRepository.delete(user);
      return ResponseEntity.ok().build();
    }
}
