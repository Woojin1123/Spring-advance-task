package com.todo.springadvancetask.entity;

import com.todo.springadvancetask.dto.schedule.ScheduleRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Table(name = "schedule")
@NoArgsConstructor
@RequiredArgsConstructor
public class Schedule extends Timestamped {

  @Id
  @Column(name = "schedule_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @NonNull
  @Column(name = "user_name", nullable = false)
  String userName;
  @NonNull
  @Column(name = "title" , nullable = false, length = 50)
  String title;
  @NonNull
  @Column(name = "contents", nullable = false, length = 200)
  String contents;

  @OneToMany(mappedBy = "schedule")
  private List<Comment> commentList = new ArrayList<>();

  public Schedule(ScheduleRequestDto requestDto) {
    this.userName = requestDto.getUserName();
    this.contents = requestDto.getContents();
    this.title = requestDto.getTitle();
  }

  public void update(ScheduleRequestDto requestDto){
    this.contents = requestDto.getContents();
    this.title = requestDto.getTitle();
  }
  public void addCommentList(Comment comment){
    this.commentList.add(comment);
    comment.setSchedule(this);//연관 관계 설정
  };

}
