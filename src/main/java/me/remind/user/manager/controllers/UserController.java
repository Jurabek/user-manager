package me.remind.user.manager.controllers;

import me.remind.user.manager.dto.UserDto;
import me.remind.user.manager.dto.UserRepoDto;
import me.remind.user.manager.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserInfoService userInfoService;

    @Autowired
    public UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("")
    public List<UserDto> getAll() {
        return userInfoService.getAll();
    }

    @GetMapping("/{id}")
    public UserDto get(@PathVariable UUID id) {
        return userInfoService.getById(id);
    }

    @PostMapping("")
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto body) {
        return new ResponseEntity<>(userInfoService.create(body), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable UUID id, @Valid @RequestBody UserDto body) {
        return new ResponseEntity<>(userInfoService.update(id, body), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        userInfoService.delete(id);
    }

    @GetMapping("/{id}/repos")
    public List<UserRepoDto> getRepositories(@PathVariable String id) {
        return userInfoService.getRepositories(UUID.fromString(id));
    }
}
