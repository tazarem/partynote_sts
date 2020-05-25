package com.yeri.partynote.utils;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Component
public class EmailServiceImpl{
	
	@Autowired
	public JavaMailSender emailSender;
	
	public void sendSimpleMessage(String sendTo, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(sendTo);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
		
	}

}
