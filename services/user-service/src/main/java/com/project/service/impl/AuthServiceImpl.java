package com.project.service.impl;

import com.project.config.JwtProvider;
import com.project.payload.dto.UserDTO;
import com.project.enums.UserRole;
import com.project.mapper.UserMapper;
import com.project.model.User;
import com.project.payload.response.AuthResponse;
import com.project.repository.UserRepository;
import com.project.service.AuthService;
import com.project.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;


    /*
    1. Check if email already exists and check role
    2. Encode password using BCrypt
    3. Save user in database
    4. Generate JWT token
    5. Return token and user information
     */

    @Override
    public AuthResponse signup(UserDTO req) throws Exception{

        if(userRepo.findByEmail(req.getEmail()).isPresent()){
            throw new Exception("user with provided email already exists");
        }

        if(req.getRole() ==  UserRole.ROLE_SYSTEM_ADMIN){
            throw new Exception("you cannot signup on system admin role");
        }

        String hashedPassword = passwordEncoder.encode(req.getPassword());

        User newUser = User.builder()
                .email(req.getEmail())
                .fullName(req.getFullName())
                .password(hashedPassword)
                .role(req.getRole())
                .phone(req.getPhone())
                .lastLogin(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepo.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
          savedUser.getEmail() , savedUser.getPassword()
        );

        String jwt = jwtProvider.generateToken(
                authentication , savedUser.getId()
        );


        return AuthResponse.builder()
                .jwt(jwt)
                .user(UserMapper.toDTO(savedUser))
                .title("Welcome" + savedUser.getFullName())
                .message("Registered successfully")
                .build();
    }

    /*
    1. Load user by email
    2.Compare password with BCrypt
    3.update lastlogin time
    4.generate JWT token
    5.Return token and user information
     */

    private Authentication authenticate(String email , String password) throws Exception {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        if(!passwordEncoder.matches(password , userDetails.getPassword())){
            throw new Exception("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails , null , userDetails.getAuthorities());
    }
    @Override
    public AuthResponse login(String email, String password) throws Exception {
       Authentication authentication = authenticate(email , password);

       User user = userRepo.findByEmail(email).orElseThrow(
               () -> new Exception("No user found")
       );

       user.setLastLogin(LocalDateTime.now());

       String jwt = jwtProvider.generateToken(authentication , user.getId());

        return AuthResponse.builder()
                .jwt(jwt)
                .user(UserMapper.toDTO(user))
                .title("Welcome Back " + user.getFullName())
                .message("Logged In successfully")
                .build();

    }


}
