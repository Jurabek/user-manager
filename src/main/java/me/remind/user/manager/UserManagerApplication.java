package me.remind.user.manager;

import me.remind.user.manager.domain.user.UserInfo;
import me.remind.user.manager.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.UUID;

@SpringBootApplication
public class UserManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserManagerApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return strings -> {
            if (userRepository.count() == 0) {
                UserInfo user1 = new UserInfo(
                        UUID.fromString("49e893bc-7819-4cf2-92be-d1acc9112e74"),
                        "Jurabek",
                        "Azizkhujaev",
                        "Software Engineer",
                        "jurabek");

                UserInfo user2 = new UserInfo(
                        UUID.fromString("c3f162ea-c8c5-4b4f-a19e-b291e59ed03e"),
                        "Bot",
                        "Bot",
                        "Bot Developer",
                        "bot1");
                userRepository.saveAll(Arrays.asList(user1, user2));
            }
        };
    }
}
