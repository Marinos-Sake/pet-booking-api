package com.petbooking.bookingapp.service;


import com.petbooking.bookingapp.core.exception.*;
import com.petbooking.bookingapp.core.filters.GenericFilters;
import com.petbooking.bookingapp.dto.UserInsertDTO;
import com.petbooking.bookingapp.dto.UserReadOnlyDTO;
import com.petbooking.bookingapp.dto.UserUpdateDTO;
import com.petbooking.bookingapp.entity.User;
import com.petbooking.bookingapp.mapper.UserMapper;
import com.petbooking.bookingapp.repository.PersonRepository;
import com.petbooking.bookingapp.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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
                    "Username already exists."
            );
        }

        if (personRepository.existsByIdentityNumber(dto.getPerson().getIdentityNumber())) {
            throw new AppObjectAlreadyExistsException(
                    "PERSON_",
                    "This identity number cannot be used."
            );
        }

//        SOS!Ignore any role provided by the user, it should always be set to "USER" for security
//        dto.setRole(Role.USER);


        User user = userMapper.mapToUserEntity(dto);
        User savedUser = userRepository.save(user);
        return userMapper.mapToReadOnlyDTO(savedUser);
    }


    public UserReadOnlyDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppObjectNotFoundException(
                        "USR_",
                        "User not found."
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
    public UserReadOnlyDTO updateMyProfile(User user, UserUpdateDTO dto) {

        if (dto.getUsername() != null) {
            String newUsername = dto.getUsername().trim();
            if (!newUsername.equalsIgnoreCase(user.getUsername())
                    && userRepository.existsByUsernameIgnoreCaseAndIdNot(newUsername, user.getId())) {
                throw new AppObjectAlreadyExistsException("USR_", "Username already exists.");
            }
        }

        if (dto.getPerson() != null && dto.getPerson().getIdentityNumber() != null) {
            if (user.getPerson() == null) {
                throw new AppValidationException("USR_", "There is no Person available for update.");
            }
            String newIdNum = dto.getPerson().getIdentityNumber().trim();
            String currentIdNum = user.getPerson().getIdentityNumber();
            if (!newIdNum.equals(currentIdNum)
                    && personRepository.existsByIdentityNumberAndIdNot(newIdNum, user.getPerson().getId())) {
                throw new AppObjectAlreadyExistsException("PERSON_", "This identity number cannot be used.");
            }
        }

        userMapper.updateUserEntityFromDTO(dto, user);
        User saved = userRepository.save(user);
        return userMapper.mapToReadOnlyDTO(saved);
    }

    @Transactional
    public void deleteMe(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public UserReadOnlyDTO getMyProfile(User user) {
        return userMapper.mapToReadOnlyDTO(user);
    }

}
