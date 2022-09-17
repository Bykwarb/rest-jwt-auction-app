package com.example.demo.event.user;

import com.example.demo.entity.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private Locale locale;
    private User user;
    public OnRegistrationCompleteEvent(User user, Locale locale){
        super(user);
        this.user = user;
        this.locale = locale;
    }


    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
