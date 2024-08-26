package com.todo.springadvancetask.dto.comment;

import com.todo.springadvancetask.entity.Comment;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class CommentResponseDto {


  private Long schduleId;
  private Long regId;
  private String name;
  private String contents;
  private String createdAt;
  private String updatedAt;
  private Long commentId;

  public CommentResponseDto(Comment comment) {
    this.schduleId = comment.getSchedule()
        .getId();
    this.regId = comment.getRegId();
    this.name = comment.getName();
    this.contents = comment.getContents();
    this.createdAt = comment.getCreatedAt()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    this.updatedAt = comment.getUpdatedAt()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    this.commentId = comment.getId();
  }
}
