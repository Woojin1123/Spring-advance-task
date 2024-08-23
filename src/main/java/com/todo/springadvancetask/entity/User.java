package com.todo.springadvancetask.entity;

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

@Entity
@Getter
@Table(name = "user")
public class User extends Timestamped{

  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "user_name")
  private String name;
  @Column(name = "email")
  private String email;

  @OneToMany(mappedBy = "user")
  private List<UserSchedule> userScheduleList = new ArrayList<>();
}
