package com.example.demo.event.lot;

import org.springframework.context.ApplicationEvent;

public class OnLotStartedEvent extends ApplicationEvent {
    public OnLotStartedEvent(Object source) {
        super(source);
    }
}
