package com.insurance.notification;

import org.springframework.stereotype.Component;

@Component
public class FagsystemStatusObserver implements Observer {

    @Override
    public void update(String message) {
        if (message.contains("Fagsystemet status update failed")) {
            // Inform the client or log the issue
            System.out.println("ALERT: " + message);
            // Add custom notification logic here (e.g., send email, push notification, etc.)
        }else{
            System.out.println("message = " + message);
        }
    }
}

