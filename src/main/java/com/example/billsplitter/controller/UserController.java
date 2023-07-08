package com.example.billsplitter.controller;


import com.example.billsplitter.dto.UserDto;
import com.example.billsplitter.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Add a new user",
            description = "Add a new user by UserDto field such as a username, first name and etc. The response is UserDto with user id")
    @PostMapping
    public UserDto add(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

}
