package com.mamasnack.entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class TLSEmail {

	/**
	   Outgoing Mail (SMTP) Server
	   requires TLS or SSL: smtp.gmail.com (use authentication)
	   Use Authentication: Yes
	   Port for TLS/STARTTLS: 587
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
	
		  InputStream is = TLSEmail.class.getResourceAsStream("/static/templete/EmailTemplete.txt");
		  System.out.println(is);
		   BufferedReader in = new BufferedReader(new InputStreamReader(is));

			String line;
			String lineT = null;
			while((line = in.readLine()) != null)
			{
			    System.out.println(line);
			    lineT +=line;
			}
			in.close();
			System.out.println("~2"+lineT);
		}
}
