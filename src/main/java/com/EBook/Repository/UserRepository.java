package com.EBook.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.EBook.Model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
public User findByEmail(String email);
}
