package com.todo.springadvancetask.controller;

import com.todo.springadvancetask.dto.comment.CommentRequestDto;
import com.todo.springadvancetask.dto.comment.CommentResponseDto;
import com.todo.springadvancetask.service.CommentService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedules")
public class CommentController {

  private final CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @PostMapping("/{scheduleId}/comments") //등록
  public ResponseEntity<CommentResponseDto> createComments(@PathVariable Long scheduleId,
      @RequestBody
      CommentRequestDto requestDto) {

    CommentResponseDto responseDto = commentService.createComments(scheduleId, requestDto);
    return ResponseEntity.status(HttpStatus.OK)
        .body(responseDto);
  }

  @GetMapping("/{scheduleId}/comments") // 일정의 댓글 전체 조회
  public ResponseEntity<List<CommentResponseDto>> findAllCommentsBySchedule(
      @PathVariable Long scheduleId) {
    List<CommentResponseDto> commentResponseDtos = commentService.findAll(scheduleId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(commentResponseDtos);
  }


  @GetMapping("/{scheduleId}/comments/{commentId}") // 일정의 댓글 Id로 조회
  public ResponseEntity<CommentResponseDto> findByIdAndSchedule(@PathVariable Long commentId,
      @PathVariable String scheduleId) {
    CommentResponseDto responseDto = commentService.findById(commentId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(responseDto);
  }

  @GetMapping("/comments/{commentId}")
  public ResponseEntity<CommentResponseDto> findById(@PathVariable Long commentId) {
    return null;
  }

}
