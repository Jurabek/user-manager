package me.remind.user.manager.mapper;

import me.remind.user.manager.components.GithubUrlParser;
import me.remind.user.manager.domain.github.UserRepositoryResponse;
import me.remind.user.manager.domain.user.UserInfo;
import me.remind.user.manager.dto.UserDto;
import me.remind.user.manager.dto.UserRepoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DtoToEntityMapperIml implements DtoToEntityMapper {

    private final GithubUrlParser githubUrlParser;

    @Autowired
    public DtoToEntityMapperIml(GithubUrlParser githubUrlParser) {
        this.githubUrlParser = githubUrlParser;
    }

    @Override
    public UserInfo fromUserDto(UserDto userDto) {
        UserInfo user = new UserInfo();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setPosition(userDto.getPosition());
        String githubUserName = githubUrlParser.getUserNameFromUrl(userDto.getGithubUrl());
        user.setGithubUserName(githubUserName);

        return user;
    }

    @Override
    public UserDto fromUserEntity(UserInfo userInfo) {
        UserDto userDto = new UserDto();
        userDto.setId(userInfo.getId());
        userDto.setName(userInfo.getName());
        userDto.setLastName(userInfo.getLastName());
        userDto.setPosition(userInfo.getPosition());
        userDto.setGithubUrl(githubUrlParser.getGithubUrlFromUserName(userInfo.getGithubUserName()));

        return userDto;
    }

    @Override
    public UserRepoDto fromUserRepoResponse(UserRepositoryResponse response) {
        UserRepoDto dto = new UserRepoDto();
        dto.setName(response.getName());
        dto.setUrl(response.getHtmlUrl());
        dto.setLanguage(response.getLanguage());

        return dto;
    }
}
