package com.nuzhd.taskmanagementsystem.service.impl;

import com.nuzhd.taskmanagementsystem.exception.UserNotFoundException;
import com.nuzhd.taskmanagementsystem.exception.UsernameTakenException;
import com.nuzhd.taskmanagementsystem.model.MyUser;
import com.nuzhd.taskmanagementsystem.repo.MyUserRepository;
import com.nuzhd.taskmanagementsystem.service.MyUserService;
import org.springframework.stereotype.Service;

@Service
public class MyUserServiceImpl implements MyUserService {

    private final MyUserRepository userRepository;

    public MyUserServiceImpl(MyUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public MyUser save(MyUser user) {

        boolean usernameTaken = existsByUsername(user.getUsername());

        if (usernameTaken) {
            throw new UsernameTakenException("Username is taken!");
        }

        return userRepository.save(user);
    }

    @Override
    public MyUser update(MyUser user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public MyUser findById(Long userId) {
        return findOrThrow(userId);
    }

    @Override
    public MyUser findByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Username not found!"));
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    private MyUser findOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(
                        () ->
                                new UserNotFoundException("User with id " + userId + " not found")
                );
    }
}
