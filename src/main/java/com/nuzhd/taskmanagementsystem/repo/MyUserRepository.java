package com.nuzhd.taskmanagementsystem.repo;

import com.nuzhd.taskmanagementsystem.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyUserRepository extends JpaRepository<MyUser, Long> {

    Optional<MyUser> findByUsername(String username);

    Boolean existsByUsername(String username);

//    Boolean existsById(Long id);
}
