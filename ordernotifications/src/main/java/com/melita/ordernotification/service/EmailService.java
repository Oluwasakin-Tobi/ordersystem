package com.melita.ordernotification.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.melita.ordernotification.configuration.MailConfigProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
	
	final MailConfigProperties configProperties;
	
	public void sendMail(String recipient, String subject, String mailBody) {

        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", configProperties.getHost());
        properties.put("mail.smtp.port", configProperties.getPort());
        properties.put("mail.smtp.ssl.enable", configProperties.getSsl());
        properties.put("mail.smtp.auth", configProperties.getAuth());

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(configProperties.getUsername(),
                		configProperties.getPassword());
//            	return new PasswordAuthentication("swapexpat@gmail.com", "xrcvxolsbfunnesz");
            }

        });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(configProperties.getFrom()));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(mailBody);
          
            log.info("sending...");
            
            Transport.send(message);
            log.info("Sent message successfully....");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

}
