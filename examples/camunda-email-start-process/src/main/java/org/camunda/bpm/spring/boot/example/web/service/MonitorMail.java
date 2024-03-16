package org.camunda.bpm.spring.boot.example.web.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.spring.boot.example.web.model.EmailRecieved;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitorMail {

	Logger logger = LoggerFactory.getLogger(MonitorMail.class);

	@Autowired
	RuntimeService runtimeService;

	public void monitor() {
		try {
			Session session = Session.getDefaultInstance(System.getProperties());
			Store store = session.getStore("imaps");
			store.connect("smtp.gmail.com", "dummyuser427@gmail.com", "euiqylcecnvbbefq");

			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_WRITE);

			Message[] messages = inbox.getMessages();
			for (Message message : messages) {
				if (!message.getFlags().contains(Flags.Flag.SEEN)) {
					// New unread email, process it
					EmailRecieved email = captureMessageDetails(message);
					logger.info("email details: ");
					logger.info(email.getMailContent());
					logger.info(email.getSender());
					logger.info(email.getDate().toString());

					startProccessInstance(email);

//                    sendNotification("New Email Received", "Subject: " + message.getSubject());
//                    message.setFlag(Flags.Flag.SEEN, true);
				}
			}

			inbox.close(true);
			store.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void startProccessInstance(EmailRecieved email) {
		// TODO Auto-generated method stub
		Map<String, Object> variables = extractVariables(email.getMailContent());
		variables.put("employeeName", email.getSender());
		logger.info("process vars before process start----> "+variables.toString());
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Mydemo", variables);
	}

	private Map<String, Object> extractVariables(String emailBody) {
	        Map<String, Object> processVariables = new HashMap<>();
	        

	        // Define regular expressions to match patterns
	        Pattern startDatePattern = Pattern.compile("\\*\\*Start Date:\\*\\* (\\d{4}-\\d{2}-\\d{2})");
	        Pattern numberOfDaysPattern = Pattern.compile("\\*\\*Number of Days:\\*\\* (\\d+)");
	        Pattern reasonPattern = Pattern.compile("\\*\\*Reason:\\*\\* (.*)");
	        // Match patterns in the email body
	        Matcher startDateMatcher = startDatePattern.matcher(emailBody);
	        Matcher numberOfDaysMatcher = numberOfDaysPattern.matcher(emailBody);
	        Matcher reasonMatcher = reasonPattern.matcher(emailBody);

	        // Extract information and put it into the HashMap
	        if (startDateMatcher.find()) {
	        	processVariables.put("startDate", startDateMatcher.group(1));
	        }
	        if (numberOfDaysMatcher.find()) {
	        	processVariables.put("numberOfDays", numberOfDaysMatcher.group(1));
	        }
	        if (reasonMatcher.find()) {
	        	processVariables.put("vacationMotivation", reasonMatcher.group(1));
	        }
	        logger.info("process vars from email----> "+processVariables.toString());
	        return processVariables;
	    }

	private EmailRecieved captureMessageDetails(Message message) throws MessagingException, IOException {
		// TODO Auto-generated method stub
		EmailRecieved email = new EmailRecieved();

		String sender = message.getFrom()[0].toString();
//		System.out.println("sender: "+sender);
		Object content = message.getContent();
		email.setSender(sender);
		email.setDate(message.getReceivedDate());
		if (content instanceof String) {
			String textContent = (String) content;
//			System.out.println("content string: "+textContent);
		} else if (content instanceof Multipart) {
			Multipart multipart = (Multipart) content;
			for (int i = 0; i < multipart.getCount(); i++) {
				BodyPart part = multipart.getBodyPart(i);
				if (part.isMimeType("text/plain")) {
//					System.out.println("content: "+part.getContent().toString());
					email.setMailContent(part.getContent().toString());
				}
			}

		}
		return email;

	}

}
