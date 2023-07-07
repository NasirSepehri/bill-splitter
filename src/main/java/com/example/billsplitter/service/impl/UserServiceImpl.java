package com.example.billsplitter.service.impl;

import com.example.billsplitter.dto.UserDto;
import com.example.billsplitter.entity.User;
import com.example.billsplitter.mapper.UserMapper;
import com.example.billsplitter.repo.UserRepository;
import com.example.billsplitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        User savedUser = userRepository.save(userMapper.toEntity(userDto));
        return userMapper.toDto(savedUser);
    }
}
