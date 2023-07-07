package com.example.billsplitter.mapper;

import com.example.billsplitter.dto.UserDto;
import com.example.billsplitter.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
