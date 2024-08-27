package com.todo.springadvancetask.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentRequestDto {

  @NotBlank
  @Size(max = 50)
  private String name;
  @NotBlank
  @Size(max = 100)
  private String contents;

}
