package com.example.monitormail.service;

import java.net.InetAddress;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.InternetAddress;

@Service
public class EmailMonitoringService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void monitorInbox() {
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
                    System.out.println("New Email Received: " + message.getSubject());
                    sendNotification("New Email Received", "Subject: " + message.getSubject());
                    message.setFlag(Flags.Flag.SEEN, true);
                }
            }

            inbox.close(true);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendNotification(String subject, String content) {
        try {
            jakarta.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            mimeMessage.setSubject(subject);
            mimeMessage.setText(content);
            mimeMessage.setRecipients(jakarta.mail.Message.RecipientType.TO, "shaleengovil2000@gmail.com");
            javaMailSender.send(mimeMessage);
            System.out.println("mail sent..");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

