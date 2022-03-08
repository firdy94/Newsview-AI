package ibf2022.paf.newsserver2.service;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Value("${SPRING_MAIL_USERNAME}")
	// @Value("${spring.mail.username")
	private String username;

	@Value("${SPRING_MAIL_PASSWORD}")
	// @Value("${spring.mail.username")
	private String password;

	@Value("${spring.mail.host}")
	private String host;

	public void sendEmail(String to, String body, String topic) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(host);
		mailSender.setPort(587);

		mailSender.setUsername(username);
		mailSender.setPassword(password);

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(System.getenv("emailhost" + "@mail.com"));
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(topic);
		simpleMailMessage.setText(body);
		mailSender.send(simpleMailMessage);
	}

}
