package com.todo.springadvancetask.controller;

import com.todo.springadvancetask.dto.schedule.ScheduleRequestDto;
import com.todo.springadvancetask.dto.schedule.ScheduleResponseDto;
import com.todo.springadvancetask.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
  public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(scheduleService.findById(id));
  }

  @GetMapping // 전체조회
  public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules(
      @RequestParam(name = "page_no", defaultValue = "0") int pageNo,
      @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
    List<ScheduleResponseDto> responseDtos = scheduleService.getAllSchedules(pageNo, pageSize);
    return ResponseEntity.status(HttpStatus.OK)
        .body(responseDtos);
  }

  @PostMapping
  public ResponseEntity<ScheduleResponseDto> createSchedule(@Valid @RequestBody ScheduleRequestDto requestDto,
      HttpServletRequest req) {
    ScheduleResponseDto responseDto = scheduleService.createSchedule(requestDto,req);
    return ResponseEntity.status(HttpStatus.OK)
        .body(responseDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id,
      @RequestBody ScheduleRequestDto requestDto,HttpServletRequest req) {
    ScheduleResponseDto responseDto = scheduleService.updateSchedule(id, requestDto,req);
    return ResponseEntity.status(HttpStatus.OK)
        .body(responseDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Long> deleteSchedule(@PathVariable Long id,HttpServletRequest req) {
    Long deletedId = scheduleService.deleteSchedule(id,req);
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(deletedId);
  }
}
