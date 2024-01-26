package com.dawnsheedy.bean;

import com.dawnsheedy.model.identity.User;
import com.dawnsheedy.model.gameplay.GameSession;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class RequestContext {
    private GameSession gameSession;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    public GameSession getGameSession() {
        return gameSession;
    }
}
