package com.awantunai.bank.controller;

import com.awantunai.bank.helper.ResourceNotFoundException;
import com.awantunai.bank.model.Admin;
import com.awantunai.bank.repository.AdminRepository;
import com.awantunai.bank.controller.AdminController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/awantunai")
public class AdminController {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AdminController adminController;

    // Login to Admins
    @PostMapping("/admins/login")
    public ResponseEntity<?> login(@RequestBody Admin adminDetails) {
      try {
        Admin admin = adminRepository.findByUsername(adminDetails.getUsername());
        if (admin.getPassword().equals(adminDetails.getPassword())) {

          // Generate random UID as a session ID
          String uid = UUID.randomUUID().toString();
          admin.setSessionId(uid);
          adminRepository.save(admin);
          return ResponseEntity.badRequest().body("Key: "+uid);
        } else {
          return ResponseEntity.badRequest().body("Login failed.");
        }
      } catch (Exception e) {
        return ResponseEntity.badRequest().body("Login failed.");
      }

    }

    // Logout to Admins
    @PostMapping("/admins/logout")
    public ResponseEntity<?> logout(@RequestParam("sessionId") String sessionId, @RequestBody Admin adminDetails) {
      if (!adminController.findAdminBySessionId(sessionId)) {
        return ResponseEntity.badRequest().body("Please login first.");
      }

      try {
        Admin admin = adminRepository.findBySessionId(adminDetails.getSessionId());
        System.out.println(adminDetails.getSessionId());
        // Remove value of random UID on session ID in database
        admin.setSessionId(null);
        adminRepository.save(admin);
        return ResponseEntity.badRequest().body("Logged Out");
      } catch (Exception e) {
        return ResponseEntity.badRequest().body("Logout failed.");
      }
    }

    // Get All Admins
    @GetMapping("/admins")
    public List<Admin> getAllAdmin() {
      return adminRepository.findAll();
    }

    // Create an Admin
    @PostMapping("/admins")
    public Admin createAdmin(@Valid @RequestBody Admin admin) {
      return adminRepository.save(admin);
    }

    // Get an Admin
    @GetMapping("/admins/{id}")
    public Admin getAdminById(@PathVariable(value="id") Long adminId) {
      return adminRepository.findById(adminId)
          .orElseThrow(() -> new ResourceNotFoundException("Admin", "id", adminId));
    }

    // Delete an Admin
    @DeleteMapping("/admins/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable(value="id") Long adminId) {
      Admin admin = adminRepository.findById(adminId)
          .orElseThrow(() -> new ResourceNotFoundException("Admin", "id", adminId));

      adminRepository.delete(admin);

      return ResponseEntity.ok().build();
    }

    public Boolean findAdminBySessionId(String sessionId) {
      System.out.println("Check if user "+sessionId+" logged in");
      try {
        Integer admin = adminRepository.countBySessionId(sessionId);
        if (admin > 0) {
          return true;
        } else {
          return false;
        }
      }
      catch (Exception e) {
         System.out.println("Exception occurred");
         return false;
      }
    }
}
