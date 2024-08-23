package com.todo.springadvancetask.controller;

import com.todo.springadvancetask.dto.schedule.ScheduleRequestDto;
import com.todo.springadvancetask.dto.schedule.ScheduleResponseDto;
import com.todo.springadvancetask.service.ScheduleService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    return ResponseEntity.status(HttpStatus.OK)
        .body(scheduleService.findById(id));
  }

  @GetMapping // 전체조회
  public ResponseEntity getAllSchedules(
      @RequestParam(name = "page_no", defaultValue = "0") int pageNo,
      @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
    List<ScheduleResponseDto> responseDtos = scheduleService.getAllSchedules(pageNo, pageSize);
    return ResponseEntity.status(HttpStatus.OK)
        .body(responseDtos);
  }

  @PostMapping
  public ResponseEntity createSchedule(@RequestBody ScheduleRequestDto requestDto) {
    ScheduleResponseDto responseDto = scheduleService.createSchedule(requestDto);
    return ResponseEntity.status(HttpStatus.OK)
        .body(responseDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity updateSchedule(@PathVariable Long id,
      @RequestBody ScheduleRequestDto requestDto) {
    ScheduleResponseDto responseDto = scheduleService.updateSchedule(id, requestDto);
    return ResponseEntity.status(HttpStatus.OK)
        .body(responseDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteSchedule(@PathVariable Long id) {
    Long deletedId = scheduleService.deleteSchedule(id);
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(deletedId);
  }
}
