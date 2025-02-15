package org.example;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

public class SendMailImages {
    public static void main(String[] args) {
        final String username = "bbertosmtp@gmail.com";
        final String password = "oumi qvhg rbtm xona"; // Usa una contraseña de aplicación si tienes 2FA habilitado
        String sender = "bbertosmtp@gmail.com";
        String[] receivers = {"a23albertogc@iessanclemente.net", "bbertosmtp@gmail.com"}; // Lista de destinatarios

        // Cargar archivo de configuración SMTP
        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream("C:\\Users\\VSPC-BLACKFRIDAY\\Desktop\\PSP\\SendMailSMTP\\src\\main\\resources\\smtp.properties")) {
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

            String cid = UUID.randomUUID().toString();

            MimeBodyPart bodyPart = new MimeBodyPart();
            String htmlContent = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Mini Lesson: Suffixes for People</title>\n" +
                    "    <style>\n" +
                    "        body {\n" +
                    "            font-family: Arial, sans-serif;\n" +
                    "            line-height: 1.6;\n" +
                    "            color: pink;\n" +
                    "        }\n" +
                    "        img {\n" +
                    "            max-width: 100%; /* Ajusta el tamaño automáticamente sin sobrepasar el contenedor */\n" +
                    "            height: auto; /* Mantiene la proporción */\n" +
                    "            width: 600px; /* Puedes cambiar este valor según necesites */\n" +
                    "            display: block;\n" +
                    "            margin: 10px auto;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <h1>Hello everyone!</h1>\n" +
                    "    <p>Today, we have another mini lesson about suffixes that talk about people. As you may know, <strong>suffixes</strong> are little groups of letters that we put on the end of other words.</p>\n" +
                    "\n" +
                    "<img src=\"cid:" + cid+ "\"/>"+
            "    <h2>Professionals</h2>\n" +
                    "    <p>We can use the suffix <strong>'-er'</strong> to talk about a person who does something.</p>\n" +
                    "    <ul>\n" +
                    "        <li>teach - <strong>teacher</strong></li>\n" +
                    "        <li>learn - <strong>learner</strong></li>\n" +
                    "        <li>work - <strong>worker</strong></li>\n" +
                    "        <li>bank - <strong>banker</strong></li>\n" +
                    "        <li>dance - <strong>dancer</strong></li>\n" +
                    "    </ul>\n" +
                    "\n" +
                    "    <p>We can also use <strong>'-ist'</strong> with the same meaning.</p>\n" +
                    "    <ul>\n" +
                    "        <li>cycle - <strong>cyclist</strong></li>\n" +
                    "        <li>psychology - <strong>psychologist</strong></li>\n" +
                    "        <li>piano - <strong>pianist</strong></li>\n" +
                    "        <li>guitar - <strong>guitarist</strong></li>\n" +
                    "        <li>art - <strong>artist</strong></li>\n" +
                    "    </ul>\n" +
                    "\n" +
                    "    <p>Unfortunately, there's no easy way to know which ending to use with each word. We just need to learn the forms.</p>\n" +
                    "\n" +
                    "    <p>I hope that helps, and really good luck with your English!</p>\n" +
                    "\n" +
                    "    <div class=\"footer\">\n" +
                    "        Best regards,<br>\n" +
                    "        Edwin\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>";
            bodyPart.setContent(htmlContent, "text/html");

            MimeBodyPart imagePart = new MimeBodyPart(); imagePart.attachFile(new File("C:\\Users\\VSPC-BLACKFRIDAY\\Desktop\\PSP\\SendMailSMTP\\src\\main\\resources\\professionals.png"));
            imagePart.setContentID("<" + cid + ">");
            imagePart.setDisposition(MimeBodyPart.INLINE);

            // Adjuntar archivo
            MimeBodyPart filePart = new MimeBodyPart();
            filePart.attachFile(new File("C:\\Users\\VSPC-BLACKFRIDAY\\Desktop\\PSP\\SendMailSMTP\\src\\main\\resources\\smtp.properties"));
            filePart.setDisposition(MimeBodyPart.INLINE);

            // Crear multipart para contener cuerpo y archivo adjunto
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(filePart);
            multipart.addBodyPart(imagePart);
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
