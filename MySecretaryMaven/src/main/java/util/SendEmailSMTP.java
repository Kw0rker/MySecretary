package util;

import Secretary.Secretary;
import com.sun.mail.smtp.SMTPTransport;
import interfaces.Redirect;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class SendEmailSMTP implements Redirect {
    private static final String SMTP_SERVER = "smtp.gmail.com";
    private static final String USERNAME = "mysecretaryautoreply@gmail.com";
    private static final String PASSWORD = "ammtrhzvmzjnxcwr";
    private static final String EMAIL_FROM = USERNAME;
    private static final String EMAIL_TO_CC = "";


    private boolean sendEmail(String EMAIL_TO, String EMAIL_SUBJECT, String EMAIL_TEXT) {
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SMTP_SERVER); //optional, defined in SMTPTransport
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", true);
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        try {

            // from
            msg.setFrom(new InternetAddress(EMAIL_FROM));

            // to 
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(EMAIL_TO));

            // cc
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(EMAIL_TO));

            // subject
            msg.setSubject(EMAIL_SUBJECT);

            // content 
            msg.setText(EMAIL_TEXT);

            msg.setSentDate(new Date());

            // Get SMTPTransport
            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

            // connect
            t.connect(SMTP_SERVER, USERNAME, PASSWORD);

            // send
            t.sendMessage(msg, msg.getAllRecipients());
            System.out.println(msg + " :send to email:" + EMAIL_TO);

            t.close();
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void redirectMessage(String Message, String name, Secretary secretary, String email) {
        this.sendEmail(email, name, Message);
    }
}
