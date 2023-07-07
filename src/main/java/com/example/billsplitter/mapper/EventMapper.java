package com.example.billsplitter.mapper;


import com.example.billsplitter.dto.EventDto;
import com.example.billsplitter.dto.UserDto;
import com.example.billsplitter.entity.Event;
import com.example.billsplitter.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CostMapper.class})
public interface EventMapper {


    default User toUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        return user;
    }

    default UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        return userDto;
    }

    EventDto toDto(Event event);

    Event toEntity(EventDto eventDto);
}
