package org.camunda.bpm.spring.boot.example.web.scheduler;

import org.camunda.bpm.spring.boot.example.web.service.MonitorMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
	
	@Autowired
	private MonitorMail monitorMail;
	
	@Scheduled(fixedRate = 60000)
	public void monitorInbox() {
		System.out.println("method called...");
		monitorMail.monitor();
	}

}
