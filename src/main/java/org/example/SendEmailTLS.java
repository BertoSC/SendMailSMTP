package org.example;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.*;
import java.util.Properties;

public class SendEmailTLS {
    public static void main(String[] args) {
        final String username = "bbertosmtp@gmail.com";
        final String password = "oumi qvhg rbtm xona"; // Usa una contraseña de aplicación si tienes 2FA habilitado
        String sender = "bbertosmtp@gmail.com";
        String[] receivers = {"a23albertogc@iessanclemente.net", "bbertosmtp@gmail.com"}; // Lista de destinatarios

        // Cargar archivo de configuración SMTP
        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream("C:\\Users\\a23albertogc\\Desktop\\PSP\\SendEmailTLS\\src\\main\\resources\\smtp.properties")) {
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No se pudo cargar el archivo smtp.properties.");
            return;
        }

        // Crear sesión de correo
        Session session = Session.getInstance(props,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Crear mensaje de correo
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender, "Name1 Surnames1"));

            // Agregar múltiples destinatarios
            for (String receiver : receivers) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver, "Name2 Surnames2"));
            }

            message.setSubject("Hello from Java");
            message.setText("Email sent from Java app and captured by Mailslurper.");

            // Crear el cuerpo del mensaje en formato HTML
            MimeBodyPart bodyPart = new MimeBodyPart();
            String htmlMessage = "<html><head>"
                    + "<title>Email protocolos</title>"
                    + "</head>"
                    + "<body><h1>Hi!</h1>"
                    + "<p>The protocol used to send an email over the internet is the Simple Mail Transfer (SMTP) protocol.</p><p>I hope this information is useful</p>"
                    + "</body></html>";
            bodyPart.setContent(htmlMessage, "text/html");

            // Adjuntar archivo
            MimeBodyPart filePart = new MimeBodyPart();
            filePart.attachFile(new File("C:\\Users\\a23albertogc\\Desktop\\PSP\\SendEmailTLS\\pom.xml"));
            filePart.setDisposition(MimeBodyPart.INLINE);

            // Crear multipart para contener cuerpo y archivo adjunto
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(filePart);
            multipart.addBodyPart(bodyPart);
            message.setContent(multipart);

            // Enviar el mensaje
            Transport.send(message);
            System.out.println("Email sent.");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Error sending mail.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
