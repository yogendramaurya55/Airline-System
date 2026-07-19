package com.project.service.impl;

import com.project.payload.dto.UserDTO;
import com.project.mapper.UserMapper;
import com.project.model.User;
import com.project.repository.UserRepository;
import com.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    @Override
    public UserDTO getUserByEmail(String email) throws Exception {
        User user  =  userRepo.findByEmail(email).orElseThrow(
                () -> new Exception("No user found with provided email")
        );
        return UserMapper.toDTO(user);
    }

    @Override
    public UserDTO getUserById(Long id) throws Exception {

        User user  =  userRepo.findById(id).orElseThrow(
                () -> new Exception("No user found with provided user id")
        );
        return UserMapper.toDTO(user);

    }

    @Override
    public List<UserDTO> getAllUsers() {

        return userRepo.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }
}
