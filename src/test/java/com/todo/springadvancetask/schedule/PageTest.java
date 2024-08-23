package com.todo.springadvancetask.schedule;

import com.todo.springadvancetask.dto.schedule.ScheduleResponseDto;
import com.todo.springadvancetask.entity.Schedule;
import com.todo.springadvancetask.repository.ScheduleRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class PageTest {

  @Autowired
  private ScheduleRepository scheduleRepository;

  @Test
  @DisplayName("페이지테스트")
  @Transactional(readOnly = true)
  public void getAllSchedules() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by("updatedAt")
        .descending());
    Page<Schedule> page = scheduleRepository.findAll(pageable);
    List<ScheduleResponseDto> responseDtos = page.getContent()
        .stream()
        .map(schedule -> new ScheduleResponseDto(schedule))
        .toList();

  }
}
