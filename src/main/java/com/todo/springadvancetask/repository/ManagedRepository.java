package com.todo.springadvancetask.repository;

import com.todo.springadvancetask.entity.Managed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagedRepository extends JpaRepository<Managed,Long> {

}
