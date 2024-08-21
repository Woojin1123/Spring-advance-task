package com.todo.springadvancetask.controller;

import com.todo.springadvancetask.dto.ScheduleRequestDto;
import com.todo.springadvancetask.dto.ScheduleResponseDto;
import com.todo.springadvancetask.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

  private final ScheduleService scheduleService;

  public ScheduleController(ScheduleService scheduleService) {
    this.scheduleService = scheduleService;
  }

  @GetMapping("/{id}") //단건조회
  public ResponseEntity getScheduleById(@PathVariable Long id) {
    return null;
  }

  @GetMapping // 전체조회
  public ResponseEntity getAllSchedules() {
    return null;
  }

  @PostMapping
  public ResponseEntity createSchedule(@RequestBody ScheduleRequestDto requestDto) {
    ScheduleResponseDto responseDto = scheduleService.save(requestDto);
    return ResponseEntity.status(HttpStatus.OK)
        .body(responseDto);
  }

  @PutMapping
  public ResponseEntity updateSchedule(ScheduleRequestDto requestDto) {
    return null;
  }
}
