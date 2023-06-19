package com.OleksiiTsarenko.User_test_task.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Objects;

@Data
public class UserDto {

    @JsonProperty("user_id")
    private final int id;

    @JsonProperty("level_id")
    private final int levelId;

    private final int result;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return id == userDto.id && levelId == userDto.levelId && result == userDto.result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, levelId, result);
    }

    /*@Override
    public int compareTo(UserDto user) {
        return Integer.compare(user.result, result);
    }*/
}
