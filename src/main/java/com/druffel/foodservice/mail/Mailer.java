/*
 * Project: foodservice Package: com.druffel.foodservice.mail File: Mailer.java
 * Created: Aug 11, 2017 Author: AmonDruffel (Sophos Technology GmbH) Copyright:
 * (C) 2017 Sophos Technology GmbH
 */
package com.druffel.foodservice.mail;

import java.io.File;
import java.net.IDN;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * <b>Description:</b><br>
 * Mailer class to send Mails for Password reset function
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
public class Mailer
{

    private Session mailSession;

    private String originator;

    private String personalName;

    public Mailer(String smtpHost, Integer smtpPort, String smtpUsername, String smtpPassword, String originator, String personalName)
    {
        boolean useAuthentification = false;

        if (!smtpHost.equals("") && smtpPort != null)
        {
            this.originator = originator;
            this.personalName = personalName;

            Properties properties = new Properties();
            properties.put("mail.transport.protocol", "smtp");
            properties.put("mail.smtp.host", smtpHost);

            properties.put("mail.smtp.timeout", 3000);
            properties.put("mail.smtp.connectiontimout", 3000);

            if (smtpUsername != null && smtpUsername.length() > 0)
            {
                useAuthentification = true;
            }

            if (useAuthentification)
            {
                properties.put("mail.smtp.auth", "true");
                Authenticator auth = new Authenticator()
                {
                    /**
                     * @see javax.mail.Authenticator#getPasswordAuthentication()
                     */
                    @Override
                    public PasswordAuthentication getPasswordAuthentication()
                    {
                        return new PasswordAuthentication(smtpUsername, smtpPassword);
                    }
                };
                mailSession = Session.getInstance(properties, auth);
            }
            else
            {
                properties.put("mail.smtp.auth", "false");
                mailSession = Session.getInstance(properties);
            }

        }
    }

    public void sendHtmlMail(String subject, String htmlMessage, String[] recipients, String personalName, File file)
    {
        MimeMultipart multipart = new MimeMultipart();
        MimeMessage mimeMessage = new MimeMessage(mailSession);

        try
        {
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(htmlMessage, "text/html; charset=UTF-8");
            DataSource source = new FileDataSource(file.getAbsoluteFile());
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(file.getName());
            multipart.addBodyPart(messageBodyPart);
            

            InternetAddress originatorAddress;

            if (!personalName.equals("") || personalName.length() > 0)
            {
                originatorAddress = new InternetAddress(IDN.toASCII(originator), personalName);
            }
            else if (!this.personalName.equals(""))
            {
                originatorAddress = new InternetAddress(IDN.toASCII(originator), this.personalName);
            }
            else
            {
                originatorAddress = new InternetAddress(IDN.toASCII(originator));
            }

            mimeMessage.setFrom(originatorAddress);
            Address[] empty = new Address[0];
            mimeMessage.setRecipients(Message.RecipientType.TO, empty);

            for (String recipient : recipients)
            {
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(IDN.toASCII(recipient)));
            }

            mimeMessage.setSubject(subject, StandardCharsets.UTF_8.name());
            mimeMessage.setHeader("Content-Type", "text/html; charset=UTF-8");
            mimeMessage.setContent(multipart);
            Transport.send(mimeMessage);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void sendHtmlMail(String subject, String htmlMessage, String[] recipients, String personalName)
    {
        MimeMultipart multipart = new MimeMultipart();
        MimeMessage mimeMessage = new MimeMessage(mailSession);

        try
        {
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(htmlMessage, "text/html; charset=UTF-8");
            multipart.addBodyPart(messageBodyPart);
            
            InternetAddress originatorAddress;

            if (!personalName.equals("") || personalName.length() > 0)
            {
                originatorAddress = new InternetAddress(IDN.toASCII(originator), personalName);
            }
            else if (!this.personalName.equals(""))
            {
                originatorAddress = new InternetAddress(IDN.toASCII(originator), this.personalName);
            }
            else
            {
                originatorAddress = new InternetAddress(IDN.toASCII(originator));
            }

            mimeMessage.setFrom(originatorAddress);
            Address[] empty = new Address[0];
            mimeMessage.setRecipients(Message.RecipientType.TO, empty);

            for (String recipient : recipients)
            {
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(IDN.toASCII(recipient)));
            }

            mimeMessage.setSubject(subject, StandardCharsets.UTF_8.name());
            mimeMessage.setHeader("Content-Type", "text/html; charset=UTF-8");
            mimeMessage.setContent(multipart);
            Transport.send(mimeMessage);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
