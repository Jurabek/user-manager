package me.remind.user.manager.services;

import me.remind.user.manager.dto.UserDto;
import me.remind.user.manager.dto.UserRepoDto;

import java.util.List;
import java.util.UUID;

public interface UserInfoService {
    UserDto create(UserDto userDto);
    UserDto update(UUID id, UserDto userDto);
    List<UserDto> getAll();
    UserDto getById(UUID id);
    List<UserRepoDto> getRepositories(UUID userId);
    void delete(UUID id);
}
