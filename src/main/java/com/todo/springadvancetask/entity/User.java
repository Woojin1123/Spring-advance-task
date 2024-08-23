package com.todo.springadvancetask.entity;

import com.todo.springadvancetask.dto.user.UserRequestDto;
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
import lombok.Setter;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
public class User extends Timestamped{

  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "user_name")
  @Setter
  private String name;
  @Column(name = "email")
  @Setter
  private String email;

  @OneToMany(mappedBy = "user")
  private List<UserSchedule> userScheduleList = new ArrayList<>();

  public User(UserRequestDto requestDto) {
    this.name = requestDto.getName();
    this.email = requestDto.getEmail();
  }
}
