package com.todo.springadvancetask.service;

import com.todo.springadvancetask.dto.comment.CommentRequestDto;
import com.todo.springadvancetask.dto.comment.CommentResponseDto;
import com.todo.springadvancetask.entity.Comment;
import com.todo.springadvancetask.entity.Schedule;
import com.todo.springadvancetask.repository.CommentRepository;
import com.todo.springadvancetask.repository.ScheduleRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

  private final CommentRepository commentRepository;
  private final ScheduleRepository scheduleRepository;

  public CommentService(CommentRepository commentRepository,
      ScheduleRepository scheduleRepository) {
    this.commentRepository = commentRepository;
    this.scheduleRepository = scheduleRepository;
  }

  @Transactional
  public CommentResponseDto createComments(Long scheduleId, CommentRequestDto requestDto) {
    Schedule schedule = scheduleRepository.findById(scheduleId)
        .orElseThrow(()->new NullPointerException("일정이 존재하지 않습니다."));
    Comment comment = new Comment(requestDto);
    Long regId = commentRepository.findRegId(scheduleId);
    comment.setRegId(regId); // 일정에 따라 댓글 번호 설정
    comment.setSchedule(schedule); // 연관 관계 설정
    schedule.addCommentList(comment);
    Comment saveComment = commentRepository.save(comment);

    return new CommentResponseDto(saveComment);
  }

  public List<CommentResponseDto> findAllBySchedule(Long scheduleId) {
    Schedule schedule = scheduleRepository.findById(scheduleId)
        .orElseThrow(()->new NullPointerException("일정이 존재하지 않습니다."));
    List<Comment> commentList = commentRepository.findAllByScheduleId(scheduleId);
    List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
    CommentResponseDto commentResponseDto;
    for (Comment comment : commentList) {
      commentResponseDto = new CommentResponseDto(comment);
      commentResponseDtos.add(commentResponseDto);
    }
    return commentResponseDtos;
  }

  public CommentResponseDto findByRegId(Long scheduleId, Long regId) {
    Schedule schedule = scheduleRepository.findById(scheduleId)
        .orElseThrow(()->new NullPointerException("일정이 존재하지 않습니다."));
    for (Comment comment : schedule.getCommentList()) {
      if (regId.equals(comment.getRegId())) {
        return new CommentResponseDto(comment);
      }
    }
    throw new NullPointerException("해당하는 댓글이 없습니다.");
  }

  public List<CommentResponseDto> findAll() {
    List<Comment> commentList = commentRepository.findAll();
    List<CommentResponseDto> responseDtos = new ArrayList<>();
    for (Comment comment : commentList) {
      responseDtos.add(new CommentResponseDto(comment));
    }
    return responseDtos;
  }

  public CommentResponseDto findById(Long commentId) {
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(()->new NullPointerException("댓글이 존재하지 않습니다."));

    return new CommentResponseDto(comment);
  }


  public CommentResponseDto updateByRegId(Long scheduleId, Long regId,
      CommentRequestDto requestDto) {
    Schedule schedule = scheduleRepository.findById(scheduleId)
        .orElseThrow(()->new NullPointerException("일정이 존재하지 않습니다."));
    List<Comment> commentList = schedule.getCommentList();
    for (Comment comment : commentList) {
      if (comment.getRegId().equals(regId)) {
        comment.setName(requestDto.getName());
        comment.setContents(requestDto.getContents());
        Comment saveComment = commentRepository.save(comment);
        commentRepository.flush();
        return new CommentResponseDto(saveComment);
      }
    }
    throw new NullPointerException("해당 댓글이 존재하지 않습니다.");
  }

  public CommentResponseDto updateById(Long commentId, CommentRequestDto requestDto) {
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(()->new NullPointerException("댓글이 존재하지 않습니다."));
    comment.setContents(requestDto.getContents());
    comment.setName(requestDto.getName());
    Comment saveComment = commentRepository.save(comment);
    return new CommentResponseDto(saveComment);
  }

  public Long deleteByRegId(Long scheduleId, Long regId) {
    Schedule schedule = scheduleRepository.findById(scheduleId)
        .orElseThrow(()->new NullPointerException("일정이 존재하지 않습니다."));
    List<Comment> commentList = schedule.getCommentList();
    for (Comment comment : commentList) {
      if (comment.getRegId().equals(regId)) {
        schedule.getCommentList().remove(comment);
        return regId;
      }
    }
    throw new NullPointerException("해당 댓글이 존재하지 않습니다.");
  }

  public Long deleteById(Long commentId) {
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(()->new NullPointerException("댓글이 존재하지 않습니다."));
    commentRepository.delete(comment);
    return commentId;
  }
}
