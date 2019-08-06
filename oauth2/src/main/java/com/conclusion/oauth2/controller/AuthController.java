package com.conclusion.oauth2.controller;

import com.conclusion.oauth2.model.User;
import com.conclusion.oauth2.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AuthController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/public")
    public ResponseEntity<?> getPublicHello(@RequestBody User user) {

        if (userRepository.findByEmail(user.getEmail()) == null) {

            LoggerFactory.getLogger("##### Oauth2 - AuthController - getPublicHello #####").info(user.toString());

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return ResponseEntity.ok("Hello new user in public page.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Goodbye exist user in public page.");
        }

    }

    @GetMapping("/private")
    public ResponseEntity<?> getPrivateHello(Principal principal) {
        return ResponseEntity.ok("Hello private page. Principal is " + principal.toString());
    }

}
