package com.firdose.springSecurity.Security.service;

import com.firdose.springSecurity.Security.constants.Constants;
import com.firdose.springSecurity.Security.entity.Session;
import com.firdose.springSecurity.Security.entity.User;
import com.firdose.springSecurity.Security.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    public void generateSession(User user, String refreshToken) {
        List<Session> userSessions = sessionRepository.findByUser(user);

        if(userSessions.size() == Constants.SESSION_LIMIT){
            userSessions.sort(Comparator.comparing(Session::getLastUsedAt));
            Session oldestSession = userSessions.getFirst();
            sessionRepository.delete(oldestSession);
        }

        Session session = Session.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();
        sessionRepository.save(session);

        return;
    }

    public void validateSession(String refreshToken) {
        Session session = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new SessionAuthenticationException("Session not found wihtth refresh token: " + refreshToken));

        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);

    }
}
