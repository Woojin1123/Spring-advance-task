package com.todo.springadvancetask.dto.schedule;

import java.util.List;
import lombok.Getter;

@Getter
public class  ScheduleRequestDto {

  private Long userId;
  private String title;
  private String contents;
  private List<Long> managerIds;

}
