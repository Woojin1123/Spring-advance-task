package com.todo.springadvancetask.dto.comment;

import com.todo.springadvancetask.entity.Comment;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class CommentResponseDto {


  private Long schduleId;
  private Long sCommentId;
  private String name;
  private String contents;
  private String createdAt;
  private String updatedAt;
  private Long commentCnt;

  public CommentResponseDto(Comment comment) {
    this.schduleId = comment.getSchedule().getId();
    this.sCommentId = comment.getSCommentId();
    this.name = comment.getName();
    this.contents = comment.getContents();
    this.createdAt = comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    this.updatedAt = comment.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    this.commentCnt = comment.getId();
  }
}
