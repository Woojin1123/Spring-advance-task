package com.todo.springadvancetask.service;

import com.todo.springadvancetask.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
  private final ScheduleRepository scheduleRepository;

  public ScheduleService(ScheduleRepository scheduleRepository) {
    this.scheduleRepository = scheduleRepository;
  }
}
