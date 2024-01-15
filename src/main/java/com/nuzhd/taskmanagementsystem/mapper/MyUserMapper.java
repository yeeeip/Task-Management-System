package com.nuzhd.taskmanagementsystem.mapper;

import com.nuzhd.taskmanagementsystem.dto.MyUserDto;
import com.nuzhd.taskmanagementsystem.model.MyUser;
import org.springframework.stereotype.Component;

@Component
public class MyUserMapper {

    public MyUserDto toDto(MyUser user) {
        return new MyUserDto(
                user.getUsername(),
                user.getEmail()
        );
    }

}
