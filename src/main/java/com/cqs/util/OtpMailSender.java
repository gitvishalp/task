package com.cqs.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class OtpMailSender implements Serializable {

	private static final long serialVersionUID = -7669658508063258406L;
	private final JavaMailSender mailSender;
	
	public String sendInviteMail(String senderEmail,String empName, String tempPass,String role) throws UnsupportedEncodingException, MessagingException {
		try {
			String redirectUrl = "http://localhost:4200/login";
			MimeMessage message = mailSender.createMimeMessage();           
		    MimeMessageHelper helper = new MimeMessageHelper(message);
		    helper.setFrom("noreply@cqs.in");
		    helper.setTo(senderEmail);
		    String subject = "Invite- CQS task workbench";
		    String content = "<p>Hi " + empName + "</p>"
		            + "<p>We are delighted to inform you that CQS Admin added you in CQS Task workbench as a " + "<b>" + role +"</b>" 
		    		+ "<br>Use your registerd email as a username and this temporary password to log into your account"
		            + "<br>Your Temporary Password is:</p>"
		            + "<p><b> " + "<font face=\"Verdana\" size =\"5\" color=\"green\" >"  + tempPass + "</font></b></p>"
		            + "<br><p>Go through this link to login :"+ redirectUrl  +"</p>"
		            + "<p><b> Don't share it with anyone!! </b></p>" 
		            + "<p><font color=\"red\"> Note: this password will not be active anymore after your first login. </font></p>"
		            + "<br>"
		            + "<b>Team CQS<b>";
		    helper.setSubject(subject);
		    helper.setText(content, true);
		    mailSender.send(message); 
			return "Invite Sent";
		}catch(Exception ex) {
			return ex.toString();
		}
	}
	
	
	public String sendTaskMail(String senderEmail, String code) throws UnsupportedEncodingException, MessagingException {
		try {
			MimeMessage message = mailSender.createMimeMessage();           
		    MimeMessageHelper helper = new MimeMessageHelper(message);
		    helper.setFrom("noreply@likebook");
		    helper.setTo(senderEmail);
		     
		    String subject = "LikeBook- ForgetPassword Code";
		     
		    String content = "<p>Hello " + senderEmail + "</p>"
		            + "<p> Sorry to see that you are facing problem to get into your account."
		            + "<br>Your Temporary Password is:</p>"
		            + "<p><b> " + "<font face=\"Verdana\" size =\"5\" >" + code + "</font></b></p>"
		            + "<p><b> Use this code to login </b></p>" 
		            + "<p><font color=\"red\"> this code is valid only for first login. </font></p>"
		            + "<br>"
		            + "<b>Team LikeBook<b>";
		    helper.setSubject(subject);
		    helper.setText(content, true);
		    mailSender.send(message); 
			return "Code send to registered Email!! ";
		}catch(Exception ex) {
			return ex.toString();
		}
		
	}
	
	
	
	
}
