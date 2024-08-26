package com.todo.springadvancetask.repository;

import com.todo.springadvancetask.entity.Managed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagedRepository extends JpaRepository<Managed,Long> {

  Managed findByScheduleIdAndUserId(Long id, Long id1);

  void deleteAllByScheduleId(Long id);
}
