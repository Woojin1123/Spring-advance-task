package com.todo.springadvancetask.entity;

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
@Getter
@Table(name = "managed")
@NoArgsConstructor
public class Managed {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @Setter
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @Setter
  @JoinColumn(name = "schedule_id")
  private Schedule schedule;

  public Managed(Schedule schedule, User user){
    this.user = user;
    this.schedule = schedule;
  }
}
