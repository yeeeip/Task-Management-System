package com.nuzhd.taskmanagementsystem.service;

import com.nuzhd.taskmanagementsystem.model.MyUser;

public interface MyUserService {

    MyUser save(MyUser user);

    MyUser findByUsername(String username);

    Boolean existsByUsername(String username);
    Boolean existsById(Long id);

    MyUser update(MyUser user);

    void deleteById(Long userId);

    MyUser findById(Long userId);
}
