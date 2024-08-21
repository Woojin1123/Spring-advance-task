package com.todo.springadvancetask.repository;

import com.todo.springadvancetask.entity.Schedule;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

  Optional<Schedule> findById(Long id);

  List<Schedule> findAll();
}
