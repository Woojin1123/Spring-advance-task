package com.todo.springadvancetask.service;

import com.todo.springadvancetask.dto.schedule.ScheduleRequestDto;
import com.todo.springadvancetask.dto.schedule.ScheduleResponseDto;
import com.todo.springadvancetask.dto.user.UserResponseDto;
import com.todo.springadvancetask.entity.Managed;
import com.todo.springadvancetask.entity.Schedule;
import com.todo.springadvancetask.entity.User;
import com.todo.springadvancetask.repository.ManagedRepository;
import com.todo.springadvancetask.repository.ScheduleRepository;
import com.todo.springadvancetask.repository.UserRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScheduleService {

  private final ScheduleRepository scheduleRepository;
  private final UserRepository userRepository;

  private final ManagedRepository managedRepository;

  public ScheduleService(ScheduleRepository scheduleRepository,
      UserRepository userRepository, ManagedRepository managedRepository) {
    this.scheduleRepository = scheduleRepository;
    this.userRepository = userRepository;
    this.managedRepository = managedRepository;
  }

  @Transactional
  public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
    User user = findUser(requestDto.getUserId());
    Schedule schedule = new Schedule(requestDto);
    schedule.setUser(user); //작성자
    Schedule saveSchedule = scheduleRepository.save(schedule);
    if (requestDto.getManagerIds() != null) {
      for (Long managerid : requestDto.getManagerIds()) {
        User manager = findUser(managerid);
        System.out.println("manager등록");
        if (managedRepository.findByScheduleIdAndUserId(schedule.getId(), manager.getId())
            == null) {
          Managed managed = new Managed(schedule, manager);
          schedule.addManagedList(managed);
          managedRepository.save(managed);
        }
      }
    }
    return new ScheduleResponseDto(saveSchedule);
  }

  @Transactional(readOnly = true)
  public ScheduleResponseDto findById(Long id) {
    Schedule schedule = scheduleRepository.findById(id)
        .orElseThrow();
    List<UserResponseDto> userResponseDtos = schedule.getManagedList()
        .stream()
        .map(managed -> {
          User user = managed.getUser();
          return new UserResponseDto(user.getId(), user.getName(), user.getEmail());
        })
        .toList();

    return new ScheduleResponseDto(schedule, userResponseDtos);
  }

  @Transactional
  public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {
    Schedule schedule = scheduleRepository.findById(id)
        .orElseThrow();
    schedule.update(requestDto);
    User user = findUser(requestDto.getUserId());
    schedule.setUser(user);
    Schedule saveSchedule = scheduleRepository.save(schedule);
    ScheduleResponseDto responseDto = new ScheduleResponseDto(saveSchedule);
    managedRepository.deleteAllByScheduleId(schedule.getId());
    if (requestDto.getManagerIds() != null) {
      for (Long managerid : requestDto.getManagerIds()) {
        User manager = findUser(managerid);
        if (managedRepository.findByScheduleIdAndUserId(schedule.getId(), manager.getId())
            == null) {
          Managed managed = new Managed(schedule, manager);
          schedule.addManagedList(managed);
          managedRepository.save(managed);
        }
      }
    }
    return responseDto;
  }

  @Transactional(readOnly = true)
  public List<ScheduleResponseDto> getAllSchedules(int pageNo, int pageSize) {
    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("updatedAt")
        .descending());
    Page<Schedule> page = scheduleRepository.findAll(pageable);
    return page.getContent()
        .stream()
        .map(schedule -> new ScheduleResponseDto(schedule))
        .toList();
  }

  public Long deleteSchedule(Long id) {
    scheduleRepository.deleteById(id);
    return id;
  }

  private User findUser(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() ->
            new NullPointerException("유저가 존재하지 않습니다.")
        );
    return user;
  }

}
