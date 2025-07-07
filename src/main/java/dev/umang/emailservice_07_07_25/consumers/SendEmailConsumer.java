package dev.umang.emailservice_07_07_25.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.umang.emailservice_07_07_25.events.SendEmail;
import dev.umang.emailservice_07_07_25.utils.EmailUtil;
import org.springframework.kafka.annotation.KafkaListener;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class SendEmailConsumer {
    private ObjectMapper objectMapper;

    // This class will consume messages from the Kafka topic
    // and send emails based on the received messages.

    // You can use @KafkaListener annotation to listen to a specific topic
    // and implement the logic to send emails using an email service.

    // Example:
    // @KafkaListener(topics = "email-topic", groupId = "email-group")
    // public void consume(SendEmail sendEmail) {
    //     // Logic to send email using sendEmail object
    // }

    public SendEmailConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "send_email", groupId = "email_group")
    public void handlerForSendEmailTopic(String message) throws JsonProcessingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("umang_1@scaler.com", "egerypjygvadmqki");
            }
        };
        Session session = Session.getInstance(props, auth);

        SendEmail sendEmail = objectMapper.readValue(
                message,
                SendEmail.class);


        EmailUtil.sendEmail(
                session,
                sendEmail.getTo(),
                sendEmail.getSubject(),
                sendEmail.getBody());

    }
}
