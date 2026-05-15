package com.user.UserService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.UserService.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    //if you want to write any custom method then you can write here
}
