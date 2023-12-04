package com.openclassrooms.mdd.service;

import com.openclassrooms.dto.RegisterRequest;
import com.openclassrooms.mdd.exception.NotFoundException;
import com.openclassrooms.mdd.model.User;
import com.openclassrooms.mdd.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé : " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserById(Integer id) {
        // integer à la base
        return userRepository.findById(id);
    }

    public User addNewUser(RegisterRequest registerRequest) {
        String username = registerRequest.getUsername();
        String email = registerRequest.getEmail();
        String password = registerRequest.getPassword();

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        Timestamp now = Timestamp.from(Instant.now());
        user.setCreated_at(now);
        user.setUpdated_at(now);
        return userRepository.save(user);
    }

    public Authentication login(String email, String password) {
        // Vérification des informations d'identification de l'utilisateur
        Authentication authentication = authenticationProvider.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    public User updateUser(Integer userId, RegisterRequest updatedUserRequest) {
        Optional<User> optionalUser = userRepository.findById(userId);
    
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
    
            // Met à jour les propriétés nécessaires
            existingUser.setUsername(updatedUserRequest.getUsername());
            existingUser.setEmail(updatedUserRequest.getEmail());
            existingUser.setPassword(passwordEncoder.encode(updatedUserRequest.getPassword()));

            updateSecurityContext(updatedUserRequest.getEmail());
            
            // Enregistre la mise à jour dans la base de données
            return userRepository.save(existingUser);
        } else {
            // Gère le cas où l'utilisateur n'est pas trouvé
            throw new NotFoundException("User not found with id: " + userId);
        }
    }

    private void updateSecurityContext(String newEmail) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = this.loadUserByUsername(newEmail);
    
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                userDetails, authentication.getCredentials(), authentication.getAuthorities()
        );
    
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
    }


}
