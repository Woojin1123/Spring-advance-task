package com.todo.springadvancetask.service;

import com.todo.springadvancetask.dto.schedule.ScheduleRequestDto;
import com.todo.springadvancetask.dto.schedule.ScheduleResponseDto;
import com.todo.springadvancetask.dto.schedule.WheatherDto;
import com.todo.springadvancetask.dto.user.UserResponseDto;
import com.todo.springadvancetask.entity.Managed;
import com.todo.springadvancetask.entity.Schedule;
import com.todo.springadvancetask.entity.User;
import com.todo.springadvancetask.repository.ManagedRepository;
import com.todo.springadvancetask.repository.ScheduleRepository;
import com.todo.springadvancetask.repository.UserRepository;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ScheduleService {

  private final ScheduleRepository scheduleRepository;
  private final UserRepository userRepository;
  private final ManagedRepository managedRepository;
  private final RestTemplate restTemplate;

  public ScheduleService(ScheduleRepository scheduleRepository,
      UserRepository userRepository, ManagedRepository managedRepository,
      RestTemplateBuilder builder) {
    this.scheduleRepository = scheduleRepository;
    this.userRepository = userRepository;
    this.managedRepository = managedRepository;
    this.restTemplate = builder.build();
  }

  @Transactional
  public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
    User user = findUser(requestDto.getUserId());
    Schedule schedule = new Schedule(requestDto);
    schedule.setUser(user); //작성자
    String weather = getWeather();
    schedule.setWeather(weather);
    if (requestDto.getManagerIds() != null) {
      for (Long managerId : requestDto.getManagerIds()) {
          User manager = findUser(managerId);
          Managed managed = new Managed(schedule, manager);
          schedule.addManagedList(managed);
        }
      }
    Schedule saveSchedule = scheduleRepository.save(schedule);
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
        .orElseThrow(); //일정 찾아오기
    User newUser = findUser(requestDto.getUserId()); //바뀔 유저 Id 찾아오기
    schedule.update(requestDto, newUser); // 내용, 제목, 작성자 업데이트
    if (requestDto.getManagerIds() != null) { //매니저 변경 요청이 있을 경우
      schedule.getManagedList()
          .clear();
      for (Long managerId : requestDto.getManagerIds()) {
        User manager = findUser(managerId);
        Managed managed = new Managed(schedule, manager);
        schedule.addManagedList(managed);
      }
    }
    Schedule saveSchedule = scheduleRepository.save(schedule);
    return new ScheduleResponseDto(saveSchedule);
  }

  @Transactional(readOnly = true)
  public List<ScheduleResponseDto> getAllSchedules(int pageNo, int pageSize) {
    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("updatedAt")
        .descending());
    Page<Schedule> page = scheduleRepository.findAll(pageable);
    return page.getContent()
        .stream()
        .map(ScheduleResponseDto::new)
        .toList();
  }

  public Long deleteSchedule(Long id) {
    scheduleRepository.deleteById(id);
    return id;
  }

  private User findUser(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() ->
            new NullPointerException("유저가 존재하지 않습니다.")
        );
  }

  private String getWeather() {
    String createDate = LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("MM-dd"));
    URI uri = UriComponentsBuilder
        .fromUriString("https://f-api.github.io")
        .path("/f-api/weather.json")
        .build()
        .toUri();
    ResponseEntity<List<WheatherDto>> responseEntity = restTemplate.exchange(uri, HttpMethod.GET,
        null, new ParameterizedTypeReference<List<WheatherDto>>() {
        });
    List<WheatherDto> wheatherDtos = responseEntity.getBody();
    return wheatherDtos.stream()
        .filter(dto -> createDate.equals(dto.getDate()))
        .findFirst()
        .orElseThrow()
        .getWeather();
  }

}
