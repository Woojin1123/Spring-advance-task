package com.todo.springadvancetask.entity;

import com.todo.springadvancetask.dto.ScheduleRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

  public Schedule(ScheduleRequestDto requestDto) {
    this.userName = requestDto.getUserName();
    this.contents = requestDto.getContents();
    this.title = requestDto.getTitle();
  }

  public void update(ScheduleRequestDto requestDto){
    this.contents = requestDto.getContents();
    this.title = requestDto.getTitle();
  }

}
