package com.todo.springadvancetask.controller;

import com.todo.springadvancetask.dto.comment.CommentRequestDto;
import com.todo.springadvancetask.dto.comment.CommentResponseDto;
import com.todo.springadvancetask.service.CommentService;
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
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(responseDto);
  }

  @GetMapping("/{scheduleId}/comments") // 일정의 댓글 전체 조회
  public ResponseEntity<List<CommentResponseDto>> findAllBySchedule(
      @PathVariable Long scheduleId) {
    List<CommentResponseDto> commentResponseDtos = commentService.findAllBySchedule(scheduleId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(commentResponseDtos);
  }


  @GetMapping("/{scheduleId}/comments/{regId}") // 일정의 댓글 등록번호로 조회
  public ResponseEntity<CommentResponseDto> findByIdAndSchedule(@PathVariable Long regId,
      @PathVariable Long scheduleId) {
    CommentResponseDto responseDto = commentService.findByRegId(scheduleId, regId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(responseDto);
  }

  @GetMapping("/comments") // 전체 댓글목록 조회
  public ResponseEntity<List<CommentResponseDto>> findAll() {
    List<CommentResponseDto> responseDtos = commentService.findAll();
    return ResponseEntity.status(HttpStatus.OK)
        .body(responseDtos);
  }

  @GetMapping("/comments/{commentId}") // 전체 댓글 Id로 조회
  public ResponseEntity<CommentResponseDto> findById(@PathVariable Long commentId) {
    CommentResponseDto responseDto = commentService.findById(commentId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(responseDto);
  }

  @PutMapping("/{scheduleId}/comments/{regId}")// 일정의 댓글등록번호로 수정
  public ResponseEntity<CommentResponseDto> updateByRegId(@PathVariable Long scheduleId,
      @PathVariable Long regId, @RequestBody CommentRequestDto requestDto) {
    CommentResponseDto responseDto = commentService.updateByRegId(scheduleId, regId, requestDto);
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(responseDto);
  }

  @PutMapping("/comments/{commentId}")// 댓글 Id로 수정
  public ResponseEntity<CommentResponseDto> updateById(@PathVariable Long commentId,
      @RequestBody CommentRequestDto requestDto) {
    CommentResponseDto responseDto = commentService.updateById(commentId, requestDto);
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(responseDto);
  }

  @DeleteMapping("/{scheduleId}/comments/{regId}")
  public ResponseEntity<Long> deleteByRegId(@PathVariable Long scheduleId,
      @PathVariable Long regId) {
    Long deletedId = commentService.deleteByRegId(scheduleId, regId);
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(deletedId);
  }

  @DeleteMapping("/comments/{commentId}")
  public ResponseEntity<Long> deleteById(@PathVariable Long commentId) {
    Long id = commentService.deleteById(commentId);
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(id);
  }
}
