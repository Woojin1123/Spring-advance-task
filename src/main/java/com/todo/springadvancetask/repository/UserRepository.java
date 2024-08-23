package com.todo.springadvancetask.repository;

import com.todo.springadvancetask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
