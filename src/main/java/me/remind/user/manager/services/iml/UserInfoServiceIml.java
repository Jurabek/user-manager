package me.remind.user.manager.services.iml;

import me.remind.user.manager.domain.user.UserInfo;
import me.remind.user.manager.dto.UserDto;
import me.remind.user.manager.dto.UserRepoDto;
import me.remind.user.manager.exceptions.NotFoundException;
import me.remind.user.manager.mapper.DtoToEntityMapper;
import me.remind.user.manager.repositories.UserRepository;
import me.remind.user.manager.services.GithubApiService;
import me.remind.user.manager.services.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserInfoServiceIml implements UserInfoService {
    private final Logger logger = LoggerFactory.getLogger(UserInfoServiceIml.class);
    private final UserRepository userRepository;
    private final DtoToEntityMapper dtoMapper;
    private final GithubApiService githubApiService;

    @Autowired
    public UserInfoServiceIml(UserRepository userRepository,
                              DtoToEntityMapper dtoMapper,
                              GithubApiService githubApiService) {

        this.userRepository = userRepository;
        this.dtoMapper = dtoMapper;
        this.githubApiService = githubApiService;
    }

    @Override
    public UserDto create(UserDto userDto) {
        UserInfo user = dtoMapper.fromUserDto(userDto);
        UserInfo result = userRepository.save(user);
        return dtoMapper.fromUserEntity(result);
    }

    @Override
    public UserDto update(UUID id, UserDto userDto) {
        isUserExists(id);

        UserInfo user = dtoMapper.fromUserDto(userDto);
        UserInfo result = userRepository.save(user);
        return dtoMapper.fromUserEntity(result);
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(dtoMapper::fromUserEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserRepoDto> getRepositories(UUID userId) {
        UserInfo user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.debug("User with id " + userId + " not found!");
                    return new NotFoundException("User: " + userId + " not found!");
                });

        return githubApiService.getUserRepositories(user.getGithubUserName()).stream()
                .map(dtoMapper::fromUserRepoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getById(UUID id) {
        UserInfo user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.debug("User with id " + id + " not found!");
                    return new NotFoundException("User: " + id + " not found!");
                });
        return dtoMapper.fromUserEntity(user);
    }

    @Override
    public void delete(UUID id) {
        isUserExists(id);
        userRepository.deleteById(id);
    }

    private void isUserExists(UUID id) {
        boolean exists = userRepository.existsById(id);
        if (!exists) {
            logger.debug("User with id " + id + " not found!");
            throw new NotFoundException("User: " + id + " not found!");
        }
    }
}
