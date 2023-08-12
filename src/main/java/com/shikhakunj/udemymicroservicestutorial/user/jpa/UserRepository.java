package com.shikhakunj.udemymicroservicestutorial.user.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shikhakunj.udemymicroservicestutorial.user.User;

public interface UserRepository extends JpaRepository<User,Integer>{

}
