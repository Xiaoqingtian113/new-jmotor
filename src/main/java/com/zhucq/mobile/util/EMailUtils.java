package com.zhucq.mobile.util;

import java.util.Collection;
import java.util.HashSet;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EMailUtils {
	public static void send(String host, int port, String user, String pwd, String from){
		SimpleEmail email = new SimpleEmail();
		email.setHostName(host);
		email.setAuthentication(user, pwd);
		email.setSmtpPort(port);
		try {
			email.setFrom(from, "Sender", "utf-8");
		} catch (EmailException e) {
			e.printStackTrace();
		}
		try {
			Collection<InternetAddress> iAddress = new HashSet<InternetAddress>();
			InternetAddress[] ads = InternetAddress.parse("");
			for(InternetAddress ad : ads){
				iAddress.add(ad);
			}
			email.setTo(iAddress);
		} catch (AddressException | EmailException e) {
			e.printStackTrace();
		}
		email.setSubject("today is a good day!!!");
		email.setCharset("utf-8");
		try {
			email.setMsg("this is a test mail");
		} catch (EmailException e) {
			e.printStackTrace();
		}
		try {
			email.sendMimeMessage();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
}
