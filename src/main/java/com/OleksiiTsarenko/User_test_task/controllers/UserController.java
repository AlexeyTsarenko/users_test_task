package com.OleksiiTsarenko.User_test_task.controllers;

import com.OleksiiTsarenko.User_test_task.dtos.UserDto;
import com.OleksiiTsarenko.User_test_task.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/userinfo/{user_id}")
    public Set<UserDto> getUsersById(@PathVariable("user_id") int userId) {
        return userService.getResultsByUser(userId);
    }

    @GetMapping("/levelinfo/{level_id}")
    public Set<UserDto> getUsersByLevelId(@PathVariable("level_id") int levelId) {
        return userService.getResultsByLevel(levelId);
    }

    @PutMapping("/setinfo")
    public void setUser(@RequestBody UserDto userDto) {
        userService.setUser(userDto);
    }
}
