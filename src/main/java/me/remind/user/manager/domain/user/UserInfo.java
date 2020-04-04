package me.remind.user.manager.domain.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class UserInfo {

    public UserInfo() {
    }

    public UserInfo(@NotNull UUID id,
                    @NotBlank String name,
                    @NotBlank String lastName,
                    @NotBlank String position,
                    @NotBlank String githubUserName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.position = position;
        this.githubUserName = githubUserName;
    }

    @NotNull
    @Id
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    @NotBlank
    private String position;

    @NotBlank
    private String githubUserName;

    public String getGithubUserName() {
        return githubUserName;
    }

    public void setGithubUserName(String githubUserName) {
        this.githubUserName = githubUserName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
