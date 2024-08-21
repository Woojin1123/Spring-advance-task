package com.todo.springadvancetask.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Schedule {
  @Id
  Long id;
}
