package com.todo.springadvancetask.service;

import com.todo.springadvancetask.dto.schedule.ScheduleRequestDto;
import com.todo.springadvancetask.dto.schedule.ScheduleResponseDto;
import com.todo.springadvancetask.entity.Schedule;
import com.todo.springadvancetask.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

  private final ScheduleRepository scheduleRepository;

  public ScheduleService(ScheduleRepository scheduleRepository) {
    this.scheduleRepository = scheduleRepository;
  }

  public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
    Schedule schedule = new Schedule(requestDto);
    Schedule saveSchedule = scheduleRepository.save(schedule);
    ScheduleResponseDto responseDto = new ScheduleResponseDto(saveSchedule);
    return responseDto;
  }

  public ScheduleResponseDto findById(Long id) {
    Schedule schedule = scheduleRepository.findById(id)
        .orElseThrow();
    return new ScheduleResponseDto(schedule);
  }


  public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {
    Schedule schedule = scheduleRepository.findById(id)
        .orElseThrow();
    schedule.update(requestDto);
    Schedule saveSchedule = scheduleRepository.save(schedule);
    ScheduleResponseDto responseDto = new ScheduleResponseDto(saveSchedule);
    return responseDto;
  }
}
