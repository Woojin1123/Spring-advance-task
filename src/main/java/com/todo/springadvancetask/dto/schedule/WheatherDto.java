package com.todo.springadvancetask.dto.schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

@Getter
@NoArgsConstructor
public class WheatherDto {
  private String date;
  private String weather;

  public WheatherDto(JSONObject wheatherJson){
    this.date = wheatherJson.get("date").toString();
    this.weather = wheatherJson.get("wheather").toString();
  }
}
