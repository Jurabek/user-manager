package me.remind.user.manager.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("github")
public class GithubConfig {
    private String publicUrl;
    private GithubApiConfig api;

    public String getPublicUrl() {
        return publicUrl;
    }

    public void setPublicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
    }

    public GithubApiConfig getApi() {
        return api;
    }

    public void setApi(GithubApiConfig api) {
        this.api = api;
    }

    public static class GithubApiConfig {
        private String url;
        private String apiToken;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getApiToken() {
            return apiToken;
        }

        public void setApiToken(String apiToken) {
            this.apiToken = apiToken;
        }
    }
}


