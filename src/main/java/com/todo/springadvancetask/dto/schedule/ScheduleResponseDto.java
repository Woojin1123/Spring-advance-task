package com.todo.springadvancetask.dto.schedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.todo.springadvancetask.entity.Schedule;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleResponseDto {

  private Long id;
  private Long userId;
  private String title;
  private String contents;
  private String createdAt;
  private String updatedAt;
  private int commentCnt;


  public ScheduleResponseDto(Schedule schedule) {
    this.id = schedule.getId();
    this.userId = schedule.getUser().getId();
    this.title = schedule.getTitle();
    this.contents = schedule.getContents();
    this.createdAt = schedule.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    this.updatedAt = schedule.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    this.commentCnt = schedule.getCommentList().size();
  }
}
