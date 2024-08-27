package com.todo.springadvancetask.entity;

import com.todo.springadvancetask.dto.schedule.ScheduleRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "schedule")
@RequiredArgsConstructor
@NoArgsConstructor
public class Schedule extends Timestamped {

  @Id
  @Column(name = "schedule_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @NonNull
  @Column(name = "title", nullable = false, length = 50)
  String title;
  @NonNull
  @Column(name = "contents", nullable = false, length = 200)
  String contents;

  @ManyToOne(fetch = FetchType.LAZY)
  @Setter
  @JoinColumn(name = "writer")
  private User user;
  @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> commentList = new ArrayList<>();
  @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Managed> managedList = new ArrayList<>();

  @Setter
  @Column(name = "weather")
  private String weather;

  public Schedule(ScheduleRequestDto requestDto) {
    this.contents = requestDto.getContents();
    this.title = requestDto.getTitle();
  }

  public void update(ScheduleRequestDto requestDto, User user) {
    this.contents = requestDto.getContents();
    this.title = requestDto.getTitle();
    this.user = user;
  }

  public void addCommentList(Comment comment) {
    this.commentList.add(comment);
    comment.setSchedule(this);//연관 관계 설정
  }

  public void addManagedList(Managed managed) {
    this.managedList.add(managed);
    managed.getUser()
        .getManagedList()
        .clear();
    managed.getUser()
        .addManagedList(managed);
  }
}
