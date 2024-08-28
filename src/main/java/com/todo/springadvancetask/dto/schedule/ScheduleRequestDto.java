package com.todo.springadvancetask.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {

  @NotBlank(message = "제목을 입력해주세요")
  @Size(max = 50, message = "제목은 50자 이하로 입력해주세요")
  private String title;
  @NotBlank(message = "내용을 입력해주세요")
  @Size(max = 200, message = "내용은 200자 이하로 입력해주세요.")
  private String contents;
  private List<Long> managerIds;
}
