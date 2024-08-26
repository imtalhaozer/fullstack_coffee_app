package com.commerceapp.commerceapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.commerceapp.commerceapp.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByUserMail(String userMail);
}
