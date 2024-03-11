package com.example.monitormail.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.monitormail.service.EmailMonitoringService;

@Component
public class SchedulerConfig {

    @Autowired
    private EmailMonitoringService emailMonitoringService;

    @Scheduled(fixedRate = 60000) // Check inbox every 10 sec
    public void monitorInbox() {
        System.out.println("method called..");
        emailMonitoringService.monitorInbox();
    }
}

