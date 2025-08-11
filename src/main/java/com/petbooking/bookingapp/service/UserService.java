package com.petbooking.bookingapp.service;


import com.petbooking.bookingapp.core.exception.AppAuthenticationException;
import com.petbooking.bookingapp.core.exception.AppObjectAlreadyExistsException;
import com.petbooking.bookingapp.core.exception.AppObjectNotFoundException;
import com.petbooking.bookingapp.core.exception.AppValidationException;
import com.petbooking.bookingapp.dto.UserInsertDTO;
import com.petbooking.bookingapp.dto.UserReadOnlyDTO;
import com.petbooking.bookingapp.entity.User;
import com.petbooking.bookingapp.mapper.UserMapper;
import com.petbooking.bookingapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserReadOnlyDTO createUser(UserInsertDTO dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new AppObjectAlreadyExistsException(
                    "USR_",
                    "Username ' " + dto.getUsername() + "' is already exists."
            );
        }

        if (dto.getPerson() == null) {
            throw new AppValidationException(
                    "USR_",
                    "Person information is required to create user"
            );
        }

        //SOS!Ignore any role provided by the user, it should always be set to "USER" for security
//        dto.setRole(Role.USER);


        User user = userMapper.mapToUserEntity(dto);
        User savedUser = userRepository.save(user);
        return userMapper.mapToReadOnlyDTO(savedUser);
    }


    public UserReadOnlyDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppObjectNotFoundException(
                        "USR_",
                        "User with ID " + id + " not found."
                ));

        return userMapper.mapToReadOnlyDTO(user);
    }



    public List<UserReadOnlyDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::mapToReadOnlyDTO)
                .toList();
    }

    @Transactional
    public UserReadOnlyDTO updateUser(Long id, UserInsertDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppObjectNotFoundException("USR_", "User with ID " + id + " not found."));

        if (!user.getUsername().equals(dto.getUsername())
                && userRepository.existsByUsername(dto.getUsername())) {
            throw new AppObjectAlreadyExistsException("USR_", "Username '" + dto.getUsername() + "' already exists.");
        }

        userMapper.updateUserEntityFromDTO(dto, user);

        return userMapper.mapToReadOnlyDTO(user);
    }


    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppObjectNotFoundException("USR_", "User with ID " + id + " not found"));
        userRepository.delete(user);
    }

    public UserReadOnlyDTO getMyProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppAuthenticationException("USR_", "User not found"));
        return userMapper.mapToReadOnlyDTO(user);
    }


}
