package org.camunda.bpm.spring.boot.example.web.model;

import java.util.Date;

public class EmailRecieved {
	
	private String sender;
	
	private String mailContent;
	
	private Date date;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
