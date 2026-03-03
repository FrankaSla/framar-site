package com.framar.site.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
	private final JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String form;
	
	@Value("${framar.contact.to}")
	private String to;
	
	public MailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void sendContact(String ime, String email, String poruka) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom(form);		//šalje vaš gmail 
		msg.setTo(to);			//dolazi na vas gmail
		msg.setReplyTo(email); 	//kad kliknete Reply, ide korisniku
		msg.setSubject("FRAMAR web upit - " + ime);
		msg.setText(
				"Ime: " + ime + "\n" + 
				"Email: " + email + "\n\n" +
				"Poruka:\n" + poruka
				);
		mailSender.send(msg);
	}

}
