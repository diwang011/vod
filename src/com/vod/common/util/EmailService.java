package com.vod.common.util;

import java.util.Properties;
import org.apache.log4j.Logger;
import com.google.code.javax.mail.Authenticator;
import com.google.code.javax.mail.Message;
import com.google.code.javax.mail.PasswordAuthentication;
import com.google.code.javax.mail.Session;
import com.google.code.javax.mail.Transport;
import com.google.code.javax.mail.internet.InternetAddress;
import com.google.code.javax.mail.internet.MimeMessage;

public class EmailService
{
    private String server;
    private String port;
    private String userName;
    private String password;
    private String from;
    private String ttls;

    private final Logger logger;

    public EmailService()
    {
        logger = Logger.getLogger(this.getClass());
    }

    public void sendEmail(String to, String bcc, String subject, String content)
    {
        sendEmail(new String[] { to }, new String[] { bcc }, subject, content);
    }

    public void ajaxSendEmail(String to, String bcc, String subject, String content)
    {
        ajaxSendEmail(new String[] { to }, new String[] { bcc }, subject, content);
    }

    public void sendEmail(String to, String subject, String content)
    {
        sendEmail(new String[] { to }, subject, content);
    }

    public void ajaxSendEmail(String to, String subject, String content)
    {
        ajaxSendEmail(new String[] { to }, subject, content);
    }

    public void ajaxSendEmail(final String[] to, final String[] bcc, final String subject, final String content)
    {
        Thread sender = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                sendEmail(to, bcc, subject, content);
            }
        });

        sender.start();
    }

    public void sendEmail(String[] to, String[] bcc, String subject, String content)
    {
        Properties props = System.getProperties();
        props.put("mail.smtp.host", server);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        if (ttls != null && ttls.length() > 0 && ttls.equalsIgnoreCase("true"))
        {
            props.put("mail.smtp.starttls.enable", "true");
        }

        Session s = Session.getInstance(props, new Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(userName, password);
            }
        });
        MimeMessage mes = new MimeMessage(s);
        try
        {
            mes.setFrom(new InternetAddress(from));
            for (String str : to)
            {
                if (str != null)
                    mes.addRecipient(Message.RecipientType.TO, new InternetAddress(str));
            }
            if (bcc != null && bcc.length > 0)
            {
                for (String str : bcc)
                {
                    if (str != null)
                        mes.addRecipient(Message.RecipientType.BCC, new InternetAddress(str));
                }
            }
            mes.setSubject(subject, "utf-8");
            mes.setContent(content, "text/html; charset=utf-8");
            Transport.send(mes);
        }
        catch (Exception e)
        {
            logger.error("sendEmail exception :" + e.getMessage(), e);
        }
    }

    public void sendEmail(String[] to, String subject, String content)
    {
        sendEmail(to, null, subject, content);
    }

    public void ajaxSendEmail(String[] to, String subject, String content)
    {
        ajaxSendEmail(to, null, subject, content);
    }

    public void setServer(String server)
    {
        this.server = server;
    }

    public void setPort(String port)
    {
        this.port = port;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public void setTtls(String ttls)
    {
        this.ttls = ttls;
    }

}
