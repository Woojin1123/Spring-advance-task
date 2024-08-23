package com.todo.springadvancetask.entity;

import com.todo.springadvancetask.dto.comment.CommentRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

  @Id
  @Column(name = "comment_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Setter
  @Column(name = "register_id")
  Long regId;
  @Setter
  @Column(name = "contents", nullable = false, length = 100)
  String contents;
  @Setter
  @Column(name = "user_name", nullable = false)
  String name;

  @Setter
  @ManyToOne
  @JoinColumn(name = "schedule_id")
  private Schedule schedule;

  public Comment(CommentRequestDto requestDto) {
    this.contents = requestDto.getContents();
    this.name = requestDto.getName();
  }
}
