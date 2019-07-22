package modules.email;

// using SendGrid's Java Library
// https://github.com/sendgrid/sendgrid-java

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.mail.*;
import models.*;
import play.Logger;
import play.mvc.Http;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class SendEmail {
    private static Logger.ALogger logger = Logger.of(SendEmail.class);
    //public static String emailToSend;
    private static Vertx mailVertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(40));
    private static MailClient mailClient;
    public boolean emailDelivered = false;
    //public sentBy



 public void sendBulkEmail(String from, String password, String subject, String body) {

     MailAttachment attachment = new MailAttachment();
     attachment.setContentType("text/plain");
     attachment.setData(Buffer.buffer("attachment file"));

     MailAttachment image = new MailAttachment();
     attachment.setContentType("image/jpeg");
     attachment.setData(Buffer.buffer("image data"));
     attachment.setDisposition("inline");
     //  attachment.setContentId("<image1@example.com>");


     MailConfig config = new MailConfig()
             .setHostname("smtp.gmail.com")
             .setPort(587)
             .setStarttls(StartTLSOptions.REQUIRED)
             .setUsername(from)
             .setPassword(password);

     List<String> emailList = getEmails();

for(int i=0;i<emailList.size();i++){
     MailMessage message = new MailMessage()
             .setSubject(subject)
             .setFrom(from)
             .setTo(emailList.get(i))
             //.setAttachment()
             // .setCc("Another User <another@example.net>")
             .setHtml(body);

     mailClient = MailClient.createShared(mailVertx, config, "exampleclient");


     mailClient.sendMail(message, result -> {
         if (result.succeeded()) {

             emailDelivered = true;

             System.out.println(result.result());

         } else {
             emailDelivered = false;
             result.cause().printStackTrace();
             System.out.println(result.cause());

         }
     });
 }
          //  generateEmailReport(from,emailList,emailDelivered,);


        logger.info("-----------------------------------------------Subject |{}| Body |{}|", subject, body);

    }

    public void emailPersons(String subject, String body) {

        MailConfig config = new MailConfig()
                .setHostname("smtp.gmail.com")
                .setPort(587)
                .setStarttls(StartTLSOptions.REQUIRED)
                .setUsername("felixwanzetse@gmail.com")
                .setPassword("washioya2o18");

        List<String> emailList = getPersonEmails();
        for (int i = 0; i < emailList.size(); i++) {

            MailMessage message = new MailMessage()
                    .setSubject(subject)
                    .setFrom("felixwanzetse@gmail.com")
                    .setTo(getPersonEmails())
                    // .setCc("Another User <another@example.net>")
                    .setText(body);

            mailClient = MailClient.createShared(mailVertx, config, "exampleclient");


            mailClient.sendMail(message, result -> {
                if (result.succeeded()) {

                    System.out.println(result.result());


                } else {
                    result.cause().printStackTrace();
                    System.out.println(result.cause());

                }
            });


        }
        logger.info("-----------------------------------------------Subject |{}| Body |{}|", subject, body);

    }


    private List<String> getEmails() {
        String rawSQL = "SELECT Email_1" + "FROM TBBUSINESSES";
        List<String> emails = Branch.finder.query().where().eq("selected", Boolean.TRUE).select("Email_2").findSingleAttributeList();





        return emails;
        //return emails;


    }

    private List<String> getPersonEmails() {
        List<String> emails = Branch.finder.query().where().eq("selected", Boolean.TRUE).select("Email_1").findSingleAttributeList();



        logger.info("-----------------------------------------------Emails | {} |", emails);


        return emails;

    }

    public void sendPasswordEmail(String emailToSend, String address) {


        MailConfig config = new MailConfig()
                .setHostname("smtp.gmail.com")
                .setPort(587)
                .setStarttls(StartTLSOptions.REQUIRED)
                .setUsername("felixwanzetse@gmail.com")
                .setPassword("washioya2o18");


        MailMessage message = new MailMessage()
                .setSubject("RE: Account Creation Successful")
                .setFrom("felixwanzetse@gmail.com")
                .setTo(address)
                // .setCc("Another User <another@example.net>")
                .setText(emailToSend);

        mailClient = MailClient.createShared(mailVertx, config, "exampleclient");


        mailClient.sendMail(message, result -> {
            if (result.succeeded()) {

                System.out.println(result.result());


            } else {
                result.cause().printStackTrace();
                System.out.println(result.cause());

            }
        });


        logger.info("-----------------------------------------------Email_1 to send |{}|  Address | {} |", emailToSend, address);
    }

    public static void sendHealthCheckEmail(String emailSubject, String emailBody) {

        MailConfig config = new MailConfig()
                .setHostname("smtp.gmail.com")
                .setPort(587)
                .setStarttls(StartTLSOptions.REQUIRED)
                .setUsername("felixwanzetse@gmail.com")
                .setPassword("washioya2o18");


        MailMessage message = new MailMessage()
                .setSubject(emailSubject)
                .setFrom("felixwanzetse@gmail.com")
                .setTo("felixwanzetse@gmail.com")
                .setCc("Another User <another@example.net>")
                .setText(emailBody);

        mailClient = MailClient.createShared(mailVertx, config, "exampleclient");

        mailClient.sendMail(message, result -> {
            if (result.succeeded()) {

                System.out.println(result.result());


            } else {
                result.cause().printStackTrace();
                System.out.println(result.cause());

            }
        });


        logger.info("-----------------------------------------------Email_1 Subject |{}| Email_1 Body |{}| ", emailSubject, emailBody);


    }

    public static void generateEmailReport(String from, String to, boolean response, String sentBy, String dateSent) {


        EmailDeliveryReports emailDeliveryReports = new EmailDeliveryReports(from, to, response, sentBy, dateSent);
        emailDeliveryReports.save();


    }


}


