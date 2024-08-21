package com.todo.springadvancetask.dto;

import com.todo.springadvancetask.entity.Schedule;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {

  Long id;
  String title;
  String contents;
  String createdAt;
  String updatedAt;


  public ScheduleResponseDto(Schedule schedule) {
    this.id = schedule.getId();
    this.title = schedule.getTitle();
    this.contents = schedule.getContents();
    this.createdAt = schedule.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    this.updatedAt = schedule.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }
}
