package com.todo.springadvancetask.service;

import com.todo.springadvancetask.dto.ScheduleRequestDto;
import com.todo.springadvancetask.dto.ScheduleResponseDto;
import com.todo.springadvancetask.entity.Schedule;
import com.todo.springadvancetask.repository.ScheduleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
  private final ScheduleRepository scheduleRepository;

  public ScheduleService(ScheduleRepository scheduleRepository) {
    this.scheduleRepository = scheduleRepository;
  }

  public ScheduleResponseDto save(ScheduleRequestDto requestDto) {
    Schedule schedule = new Schedule(requestDto);
    Schedule saveSchedule = scheduleRepository.save(schedule);
    ScheduleResponseDto responseDto = new ScheduleResponseDto(saveSchedule);
    return responseDto;
  }
}
