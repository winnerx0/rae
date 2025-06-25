package com.winnerezy.simon.repositories;

import com.winnerezy.simon.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByName(String name);
    Optional<User> findByUsername(String email);
}
