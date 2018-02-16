package com.mamasnack.entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class SendEmail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3975190319142249367L;

	/**
	   Outgoing Mail (SMTP) Server
	   requires TLS or SSL: smtp.gmail.com (use authentication)
	   Use Authentication: Yes
	   Port for TLS/STARTTLS: 587
	 * @return 
	 * @throws IOException 
	 */
	public  String sendmail(String mail) throws IOException {
		final String fromEmail = "bks.ayb@gmail.com"; //requires valid gmail id
		final String password = "ab<13.ayb"; // correct password for gmail id
		final String toEmail = mail; // can be any email id 
		
		System.out.println("TLSEmail Start");
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtp.port", "587"); //TLS Port
		props.put("mail.smtp.auth", "true"); //enable authentication
		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
		
                //create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		Session session = Session.getInstance(props, auth);
		
		
/*
	    String html = "<html><head><title>Bonjour.</title></head>"
							  + "<body><p>vous pouvez réinitialiser votre mot de passe en cliquant sur ce lien! .</p>"
							  + "<a href='localhost:8080/index.html'> .. </a>"
							  + "</body></html>";
	    String htmlText = new String("<html><font color='red'>" + "jsjs" + "</font></html>");
	    
	    */
		
		String contenuMail = readTemplete().toString();
	    
			EmailUtil.sendEmail(session, toEmail,"MamaSnack- réinitialiser le mot de passe", contenuMail);
		return "OK";
		
	}

	
	
	public String readTemplete() throws IOException {
		
		  InputStream is = TLSEmail.class.getResourceAsStream("/static/templete/EmailTemplete.txt");
		
		   BufferedReader in = new BufferedReader(new InputStreamReader(is));

			String line;
			String lineT = "";
			while((line = in.readLine()) != null)
			{
			   
			    lineT +=line;
			}
			in.close();
			//System.out.println("~2"+lineT);
			return lineT;
			
		}
	
}