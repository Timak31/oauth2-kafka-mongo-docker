package com.conclusion.oauth2.service;

import com.conclusion.oauth2.model.MongoUser;
import com.conclusion.oauth2.model.User;
import com.conclusion.oauth2.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MongoUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public MongoUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);

        if (user != null) {
            return new MongoUser(user, Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        } else {
            return null;
        }
    }
}
