package me.remind.user.manager.components;

import me.remind.user.manager.config.GithubConfig;
import me.remind.user.manager.exceptions.InvalidUrlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class GithubUrlParser {
    private final GithubConfig githubConfig;
    private final Logger logger = LoggerFactory.getLogger(GithubUrlParser.class);

    @Autowired
    public GithubUrlParser(GithubConfig githubConfig) {
        this.githubConfig = githubConfig;
    }

    public String getUserNameFromUrl(String githubUrl) {
        try {
            URL url = new URL(githubUrl);
            return url.getPath().replaceFirst("/", "");
        } catch (Exception ex) {
            logger.error("Cannot parse URL: " + githubUrl + "!");
            throw new InvalidUrlException(ex);
        }
    }

    public String getGithubUrlFromUserName(String userName) {
        return githubConfig.getPublicUrl() + "/" + userName;
    }
}
