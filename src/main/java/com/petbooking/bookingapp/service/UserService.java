package com.petbooking.bookingapp.service;


import com.petbooking.bookingapp.core.exception.*;
import com.petbooking.bookingapp.core.filters.GenericFilters;
import com.petbooking.bookingapp.dto.UserInsertDTO;
import com.petbooking.bookingapp.dto.UserReadOnlyDTO;
import com.petbooking.bookingapp.dto.UserUpdateDTO;
import com.petbooking.bookingapp.entity.Person;
import com.petbooking.bookingapp.entity.User;
import com.petbooking.bookingapp.mapper.UserMapper;
import com.petbooking.bookingapp.repository.PersonRepository;
import com.petbooking.bookingapp.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PersonRepository personRepository;

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

    @Transactional(readOnly = true)
    public Page<UserReadOnlyDTO> getAllForAdmin(GenericFilters filters) {
        return userRepository
                .findAll(filters.toPageable())
                .map(userMapper::mapToReadOnlyDTO);
    }

    @Transactional
    public User updateMyProfile(Long currentUserId, UserUpdateDTO dto) {
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found: " + currentUserId));

        userMapper.updateUserEntityFromDTO(dto, user);
        return userRepository.save(user);
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
