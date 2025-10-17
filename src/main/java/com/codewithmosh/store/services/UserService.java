package com.codewithmosh.store.services;

import com.codewithmosh.store.dtos.ChangePasswordRequest;
import com.codewithmosh.store.dtos.RegisterUserRequest;
import com.codewithmosh.store.dtos.UpdateUserRequest;
import com.codewithmosh.store.dtos.UserDto;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.mappers.UserMapper;
import com.codewithmosh.store.repositories.UserRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAllUsers(String sort) {
        if (!Set.of("id", "name", "email").contains(sort))
            sort = "name";
        return userRepository.findAll(Sort.by(sort)).stream().map(userMapper::toDto).toList();
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toDto).orElse(null);
    }

    public UserDto createUser(RegisterUserRequest request) {
        User user = userMapper.toEntity(request);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public boolean changePassword(Long id, ChangePasswordRequest request) {
        
        User user = userRepository.findById(id).orElse(null);
        if (user == null){
            return false;
        }
        if (!user.getPassword().equals(request.getOldPassword())){
            throw new IllegalArgumentException("Contraseña incorrecta");
        }
        
        user.setPassword(request.getNewPassword());
        userRepository.save(user);
        return true;
    }

    public UserDto updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null){
            return null;
        }
        userMapper.update(request, user);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public boolean deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null){
            return false;
        }
        userRepository.delete(user);
        return true;
    }
    
    
}
