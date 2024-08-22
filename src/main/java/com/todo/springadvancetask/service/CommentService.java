package com.todo.springadvancetask.service;

import com.todo.springadvancetask.dto.comment.CommentRequestDto;
import com.todo.springadvancetask.dto.comment.CommentResponseDto;
import com.todo.springadvancetask.entity.Comment;
import com.todo.springadvancetask.entity.Schedule;
import com.todo.springadvancetask.repository.CommentRepository;
import com.todo.springadvancetask.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

  private final CommentRepository commentRepository;
  private final ScheduleRepository scheduleRepository;

  public CommentService(CommentRepository commentRepository,
      ScheduleRepository scheduleRepository) {
    this.commentRepository = commentRepository;
    this.scheduleRepository = scheduleRepository;
  }

  public List<CommentResponseDto> findAll(Long scheduleId) {
    Schedule schedule = scheduleRepository.findById(scheduleId)
        .orElseThrow();
    List<Comment> commentList = commentRepository.findAllByScheduleId(scheduleId);
    List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
    CommentResponseDto commentResponseDto;
    for (Comment comment : commentList) {
      commentResponseDto = new CommentResponseDto(comment);
      commentResponseDtos.add(commentResponseDto);
    }
    return commentResponseDtos;
  }

  @Transactional
  public CommentResponseDto createComments(Long scheduleId, CommentRequestDto requestDto) {

    Schedule schedule = scheduleRepository.findById(scheduleId)
        .orElseThrow();

    Comment comment = new Comment(requestDto);
    Long commentId = commentRepository.findCommentId(scheduleId);
    comment.setSCommentId(commentId); // 일정에 따라 댓글 번호 설정

    comment.setSchedule(schedule); // 연관 관계 설정
    schedule.addCommentList(comment);

    scheduleRepository.save(schedule);
    Comment saveComment = commentRepository.save(comment);
    CommentResponseDto responseDto = new CommentResponseDto(saveComment);
    return responseDto;
  }

  public CommentResponseDto findById(Long commentId) {
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow();
    return new CommentResponseDto(comment);
  }
}
