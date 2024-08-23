package com.todo.springadvancetask.service;

import com.todo.springadvancetask.dto.schedule.ScheduleRequestDto;
import com.todo.springadvancetask.dto.schedule.ScheduleResponseDto;
import com.todo.springadvancetask.entity.Schedule;
import com.todo.springadvancetask.repository.ScheduleRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional(readOnly = true)
  public List<ScheduleResponseDto> getAllSchedules(int pageNo, int pageSize) {
    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("updatedAt")
        .descending());
    Page<Schedule> page = scheduleRepository.findAll(pageable);
    return page.getContent()
        .stream()
        .map(schedule -> new ScheduleResponseDto(schedule))
        .toList();
  }


  public Long deleteSchedule(Long id) {
    scheduleRepository.deleteById(id);
    return id;
  }
}
