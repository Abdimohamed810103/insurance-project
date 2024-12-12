package com.insurance.notification;

import org.springframework.stereotype.Component;

@Component
public class FagsystemStatusObserver implements Observer {

    @Override
    public void update(String message) {
        System.out.println("message = " + message);
    }
}

