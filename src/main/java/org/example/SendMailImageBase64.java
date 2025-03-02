package org.example;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;
import java.util.UUID;

public class SendMailImageBase64 {
    public static void main(String[] args) {
/*
      // Ejemplo de CODIFICAR LA IMAGEN A BASE64

        File file=new File("/path/to/file");
FileInputStream imageInFile=new FileInputStream(file);
byte imageData[]=new byte[(int)file.length()];
imageInFile.read(imageData);
String imageB64=Base64.getEncoder().encodeToString(imageData);

<img src="data:image/png;base64, imageB64" />

// Texto que hay que formatear en el mail:


Hello everyone!
Let's talk about when we use 'the' with transport.

transports
First, we often use the when we are talking about a form of transport as a general idea.We usually do this with public transport (not with cars or bikes) and we usually useverbs such as take, be on, get on and get off:

We took the bus to school.
Julie's on the train at the moment.
She gets off the underground in central London.
In all of these examples, I'm not talking about a particular bus, train or plane but rather the system of transport as an idea.

However, we use 'no article' when we use a form of transport with by:

We travelled by plane.
He goes to work by bus.
We went to Scotland by train.
Remember, we can't say 'by foot' or 'by feet' when we're talking about walking. We say 'on foot' (also no article)

I hope that helps, and really good luck with your English!


Pam

*/

            final String username = "bbertosmtp@gmail.com";
            final String password = ""; // Usa una contraseña de aplicación si tienes 2FA habilitado
            String sender = "bbertosmtp@gmail.com";
            String[] receivers = {"galiciano83@hotmail.com", "bbertosmtp@gmail.com"}; // Lista de destinatarios

            // Cargar archivo de configuración SMTP
            Properties props = new Properties();
            try (FileInputStream input = new FileInputStream("src\\main\\resources\\smtp.properties")) {
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

                // Agregar destinatarios
                for (String receiver : receivers) {
                    message.addRecipient(Message.RecipientType.TO,  new InternetAddress(receiver, "Name2 Surnames2"));
                }

                message.addRecipient(Message.RecipientType.CC, new InternetAddress("berto@yopmail.com"));

                message.setSubject("Hello from Java");
                message.setText("Email sent from Java app and captured by Mailslurper.");

                // Generar un ID único para la imagen

                File file=new File("src\\main\\resources\\transports.png");
                FileInputStream imageInFile=new FileInputStream(file);
                byte imageData[]=new byte[(int)file.length()];
                imageInFile.read(imageData);
                String imageB64= Base64.getEncoder().encodeToString(imageData);

                MimeBodyPart bodyPart = new MimeBodyPart();
                String htmlContent = "<!DOCTYPE html>\n" +
                        "<html lang='es'>\n" +
                        "<head>\n" +
                        "    <meta charset='UTF-8'>\n" +
                        "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n" +
                        "    <title>Uso de 'the' con transportes</title>\n" +

                                "    <style>\n" +
                        "        body {\n" +
                        "            font-family: Arial, sans-serif;\n" +
                        "            line-height: 1.6;\n" +
                        "            color: green;\n" +
                        "        }\n" +
                        "        img {\n" +
                        "            max-width: 100%; \n" +
                        "            height: auto; \n" +
                        "            width: 600px;\n" +
                        "            display: block;\n" +
                        "            margin: 10px auto;\n" +
                        "        }\n" +
                        "    </style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <h1>Hello everyone!</h1>\n" +
                        "    <p>Let's talk about when we use 'the' with transport.</p>\n" +
                        "<img src=\"data:image/png;base64, " + imageB64 + "\" />"+
                        "    <h2>Transports</h2>\n" +
                        "    <p>First, we often use <strong>the</strong> when we are talking about a form of transport as a general idea. We usually do this with public transport (not with cars or bikes) and we usually use verbs such as <em>take</em>, <em>be on</em>, <em>get on</em>, and <em>get off</em>:</p>\n" +
                        "    <ul>\n" +
                        "        <li>We took <strong>the</strong> bus to school.</li>\n" +
                        "        <li>Julie's on <strong>the</strong> train at the moment.</li>\n" +
                        "        <li>She gets off <strong>the</strong> underground in central London.</li>\n" +
                        "    </ul>\n" +
                        "    <p>In all of these examples, I'm not talking about a particular bus, train, or plane but rather the system of transport as an idea.</p>\n" +
                        "    <p>However, we use <strong>no article</strong> when we use a form of transport with <em>by</em>:</p>\n" +
                        "    <ul>\n" +
                        "        <li>We travelled <em>by</em> plane.</li>\n" +
                        "        <li>He goes to work <em>by</em> bus.</li>\n" +
                        "        <li>We went to Scotland <em>by</em> train.</li>\n" +
                        "    </ul>\n" +
                        "    <p>Remember, we can't say <em>by foot</em> or <em>by feet</em> when we're talking about walking. We say <em>on foot</em> (also no article).</p>\n" +
                        "    <p>I hope that helps, and really good luck with your English!</p>\n" +
                        "    <p>Pam</p>\n" +
                        "</body>\n" +
                        "</html>";
                bodyPart.setContent(htmlContent, "text/html");

                // Archivo adjunto
                MimeBodyPart filePart = new MimeBodyPart();
                filePart.attachFile(new File("src\\main\\resources\\smtp.properties"));
                filePart.setDisposition(MimeBodyPart.INLINE);

                // Crear el multipart para las partes del mail
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(bodyPart);
                message.setContent(multipart);

                // Envío
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
