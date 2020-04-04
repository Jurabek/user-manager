package me.remind.user.manager.mapper;

import me.remind.user.manager.domain.github.UserRepositoryResponse;
import me.remind.user.manager.domain.user.UserInfo;
import me.remind.user.manager.dto.UserDto;
import me.remind.user.manager.dto.UserRepoDto;

public interface DtoToEntityMapper {
    UserInfo fromUserDto(UserDto userDto);
    UserDto fromUserEntity(UserInfo userInfo);
    UserRepoDto fromUserRepoResponse(UserRepositoryResponse response);
}
