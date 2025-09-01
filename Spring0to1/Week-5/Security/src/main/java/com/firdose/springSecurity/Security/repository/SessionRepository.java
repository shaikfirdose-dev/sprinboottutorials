package com.firdose.springSecurity.Security.repository;

import com.firdose.springSecurity.Security.entity.Session;
import com.firdose.springSecurity.Security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findByUser(User user);

    Optional<Session> findByRefreshToken(String refreshToken);
}
