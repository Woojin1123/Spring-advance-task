package com.todo.springadvancetask.dto.schedule;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {

  private Long userId;
  private String title;
  private String contents;

}
