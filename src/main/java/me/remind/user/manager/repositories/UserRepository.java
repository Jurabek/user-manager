package me.remind.user.manager.repositories;

import me.remind.user.manager.domain.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, UUID> {
}
