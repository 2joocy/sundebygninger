package DbHandler;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailTLS {

    private final static String USERNAME = "bingobangomail@gmail.com";
    private final static String PASSWORD = "bingopassword";

    /**
     * Send an email to the given address, with the subject and message.
     * @param toEmail The receiving email address.
     * @param subject The subject header of the email.
     * @param message The message that you wish to send.
     * @return Returns a message, which can include an error.
     */
    public static String sendMessage(String toEmail, String subject, String message) {
        Session s = getSession(USERNAME, PASSWORD);
        Message m = createMessage(s, USERNAME, toEmail, subject, message);
        try {
            Transport.send(m);
        } catch (MessagingException e) {
            return e.getMessage();
        }
        return "No errors.";
    }

    private static Session getSession(String username, String password) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        return Session.getInstance(props, getAuthenticator(username, password));
    }

    private static Authenticator getAuthenticator(String username, String password) {
        return new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
    }

    private static Message createMessage(Session session, String fromEmail, String toEmail, String subject, String body) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}