package me.remind.user.manager.services;

import me.remind.user.manager.domain.github.UserRepositoryResponse;
import me.remind.user.manager.dto.UserDto;
import me.remind.user.manager.dto.UserRepoDto;
import me.remind.user.manager.exceptions.NotFoundException;
import me.remind.user.manager.mapper.DtoToEntityMapper;
import me.remind.user.manager.domain.user.UserInfo;
import me.remind.user.manager.repositories.UserRepository;
import me.remind.user.manager.services.iml.UserInfoServiceIml;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserInfoServiceUnitTests {
    @Mock
    private UserRepository repository;

    @Mock
    private DtoToEntityMapper mapper;

    @Mock
    private GithubApiService githubApiService;

    @InjectMocks
    private UserInfoServiceIml userService;

    @Test
    public void given_valid_dto_should_create() {
        // arrange
        UserDto userDto = new UserDto();
        UserInfo entity = new UserInfo();
        UserDto expected = new UserDto();

        when(mapper.fromUserDto(userDto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.fromUserEntity(entity)).thenReturn(expected);

        // act
        UserDto result = userService.create(userDto);

        // assert
        assertEquals(expected, result);
    }

    @Test
    public void given_valid_dto_should_update() {
        // arrange
        UUID userId = UUID.randomUUID();
        UserDto userDto = new UserDto();
        UserInfo entity = new UserInfo();
        UserDto expected = new UserDto();

        when(mapper.fromUserDto(userDto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(repository.existsById(userId)).thenReturn(true);
        when(mapper.fromUserEntity(entity)).thenReturn(expected);

        // act
        UserDto result = userService.update(userId, userDto);

        // assert
        assertEquals(expected, result);
    }

    @Test
    public void given_non_existing_userId_update_should_throw() {
        // arrange
        UUID nonExistingUserId = UUID.randomUUID();
        UserDto userDto = new UserDto();
        when(repository.existsById(nonExistingUserId)).thenReturn(false);

        // act
        Assertions.assertThrows(NotFoundException.class, () -> {
            userService.update(nonExistingUserId, userDto);
        }, "User: " + nonExistingUserId.toString() + " not found!");
    }

    @Test
    public void test_getAllUsers() {
        // arrange
        UserDto userDto = new UserDto();
        UserInfo entity = new UserInfo();
        List<UserInfo> entities = Collections.singletonList(entity);
        List<UserDto> expectedResult = Collections.singletonList(userDto);

        when(repository.findAll()).thenReturn(entities);
        when(mapper.fromUserEntity(entity)).thenReturn(userDto);

        // act
        List<UserDto> result = userService.getAll();

        // assert
        assertEquals(expectedResult, result);
    }

    @Test
    public void given_existing_UserName_should_return_repositories() {
        // arrange
        UUID userId = UUID.randomUUID();
        UserInfo entity = new UserInfo();
        entity.setGithubUserName("test-user");
        UserRepositoryResponse response = new UserRepositoryResponse();
        UserRepoDto userRepo = new UserRepoDto();
        when(repository.findById(userId)).thenReturn(Optional.of(entity));
        when(githubApiService.getUserRepositories("test-user"))
                .thenReturn(Collections.singletonList(response));
        when(mapper.fromUserRepoResponse(response)).thenReturn(userRepo);

        // act
        List<UserRepoDto> result = userService.getRepositories(userId);

        // assert
        assertEquals(Collections.singletonList(userRepo), result);
    }

    @Test
    public void given_non_existing_userId_repositories_should_throw() {
        // arrange
        UUID nonExistingUserId = UUID.randomUUID();
        when(repository.findById(nonExistingUserId)).thenReturn(Optional.empty());

        // act
        Assertions.assertThrows(NotFoundException.class, () -> {
            userService.getRepositories(nonExistingUserId);
        });
    }

    @Test
    public void given_valid_userId_get_should_return() {
        // arrange
        UUID userId = UUID.randomUUID();
        UserInfo entity = new UserInfo();
        UserDto expectedDto = new UserDto();

        when(repository.findById(userId)).thenReturn(Optional.of(entity));
        when(mapper.fromUserEntity(entity)).thenReturn(expectedDto);

        // act
        UserDto result = userService.getById(userId);

        // assert
        assertEquals(expectedDto, result);
    }

    @Test
    public void given_non_existing_userId_get_should_throw() {
        // arrange
        UUID nonExistingUserId = UUID.randomUUID();
        when(repository.findById(nonExistingUserId)).thenReturn(Optional.empty());

        // act
        Assertions.assertThrows(NotFoundException.class, () -> {
            userService.getById(nonExistingUserId);
        });
    }

    @Test
    public void given_valid_id_should_delete_entity() {
        // arrange
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(true);
        doNothing().when(repository).deleteById(id);

        // act
        userService.delete(id);

        // assert
        Mockito.verify(repository, times(1)).deleteById(id);
    }
}
