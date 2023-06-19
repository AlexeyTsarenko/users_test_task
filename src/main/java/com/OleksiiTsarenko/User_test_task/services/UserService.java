package com.OleksiiTsarenko.User_test_task.services;

import com.OleksiiTsarenko.User_test_task.dtos.UserDto;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;

@Service
public class UserService {

    private final Map<Integer, NavigableSet<UserDto>> userMap = new ConcurrentHashMap<>();
    private final Comparator<UserDto> resultLevelComparator = Comparator.comparing(UserDto::getResult, Comparator.reverseOrder()).thenComparing(UserDto::getLevelId);

    private final Map<Integer, NavigableSet<UserDto>> levelMap = new ConcurrentHashMap<>();
    private final Comparator<UserDto> resultUserIdComparator = Comparator.comparing(UserDto::getResult, Comparator.reverseOrder()).thenComparing(UserDto::getId);

    private static final int LIMIT = 20;

    public void setUser(UserDto userDto) {
        populateMap(userMap, userDto.getId(), userDto, resultLevelComparator);
        populateMap(levelMap, userDto.getLevelId(), userDto, resultUserIdComparator);
    }

    private void populateMap(Map<Integer, NavigableSet<UserDto>> userMap, int identifier, UserDto userDto, Comparator<UserDto> comparator) {
        NavigableSet<UserDto> userDtoSet = userMap.computeIfAbsent(identifier, k -> new TreeSet<>(comparator));
        synchronized (userDtoSet) {
            userDtoSet.add(userDto);
            if (userDtoSet.size() > LIMIT) {
                userDtoSet.pollLast();
            }
        }
    }

    public Set<UserDto> getResultsByUser(int userId) {
        Set<UserDto> set = userMap.get(userId);
        if (isNull(set)) {
            return Collections.emptySet();
        }
        synchronized (set) {
            //new set is need to avoid an edge cases where set gets a new entry and get returned before populateMap.pollLast(), resulting in 21 elements returned
            return new LinkedHashSet<>(set);
        }
    }

    public Set<UserDto> getResultsByLevel(int levelId) {
        Set<UserDto> set = levelMap.get(levelId);
        if (isNull(set)) {
            return Collections.emptySet();
        }
        synchronized (set) {
            return new LinkedHashSet<>(set);
        }
    }
}
