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
import models.EmailReports;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class SendEmail {
    private static Logger.ALogger logger = Logger.of(SendEmail.class);
    //public static String emailToSend;
    private static Vertx mailVertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(40));
    private static MailClient mailClient;
    public static boolean emailDelivered = false;
    static String DateReceived=" ";
    //static String SenderName = Result.session().get("Username");
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

    String SentDate=datestring();
    String SentBy=from;
    String SentTo=emailList.get(i);
    String type="BUSINESS EMAIL";
    String messge=subject+" "+body;
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
             DateReceived=datestring();



             System.out.println(result.result());

         } else {
             emailDelivered = false;
             result.cause().printStackTrace();
             System.out.println(result.cause());

         }
     });
     boolean received=emailDelivered;
     String SenderName = "agile";
    
     emailreporting( type,SenderName,SentBy,SentTo, received,SentDate,DateReceived,messge);
 }
          //  generateEmailReport(from,emailList,emailDelivered,);


        logger.info("-----------------------------------------------Subject |{}| Body |{}|", subject, body);

    }

    public void emailPersons(String subject, String body) {
    String SentDate=datestring();
    String SentBy="felixwanzetse@gmail.com";
    
    String type="PERSON EMAIL";
    String messge=subject+" "+body;

        MailConfig config = new MailConfig()
                .setHostname("smtp.gmail.com")
                .setPort(587)
                .setStarttls(StartTLSOptions.REQUIRED)
                .setUsername("felixwanzetse@gmail.com")
                .setPassword("washioya2o18");

        List<String> emailList = getPersonEmails();
        for (int i = 0; i < emailList.size(); i++) {
             String SentTo=emailList.get(i);

            MailMessage message = new MailMessage()
                    .setSubject(subject)
                    .setFrom("felixwanzetse@gmail.com")
                    .setTo(getPersonEmails())
                    // .setCc("Another User <another@example.net>")
                    .setHtml(body);

            mailClient = MailClient.createShared(mailVertx, config, "exampleclient");


            mailClient.sendMail(message, result -> {
                if (result.succeeded()) {
                     DateReceived=datestring();

                    System.out.println(result.result());


                } else {
                    result.cause().printStackTrace();
                    System.out.println(result.cause());

                }
            });
            boolean received=emailDelivered;
     
     String SenderName = "agile";

emailreporting( type,SenderName,SentBy,SentTo, received,SentDate,DateReceived,messge);

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
    String SentDate=datestring();
    String SentBy="felixwanzetse@gmail.com";
    
    String type="PASSWORD EMAIL";
    String messge=emailToSend;
    String SentTo=address;


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
                .setHtml(emailToSend);

        mailClient = MailClient.createShared(mailVertx, config, "exampleclient");


        mailClient.sendMail(message, result -> {
            if (result.succeeded()) {

                System.out.println(result.result());
                emailDelivered=true;


            } else {
                result.cause().printStackTrace();
                System.out.println(result.cause());

            }
        });
        
boolean received=emailDelivered;
     
     String SenderName = "agile";

emailreporting( type,SenderName,SentBy,SentTo, received,SentDate,DateReceived,messge);

        logger.info("-----------------------------------------------Email_1 to send |{}|  Address | {} |", emailToSend, address);
    }

    public static void sendHealthCheckEmail(String emailSubject, String emailBody) {

    String SentDate=datestring();
    String SentBy="felixwanzetse@gmail.com";
    String SentTo="felixwanzetse@gmail.com";
    String type="HEALTHCHECK EMAIL";
    String messge=emailSubject+" "+emailBody;

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
               .setHtml(emailBody);

        mailClient = MailClient.createShared(mailVertx, config, "exampleclient");

        mailClient.sendMail(message, result -> {
            if (result.succeeded()) {

                System.out.println(result.result());
                 emailDelivered=true;


            } else {
                result.cause().printStackTrace();
                System.out.println(result.cause());

            }
        });

           boolean received=emailDelivered;
            String SenderName = "agile";
             emailreporting( type,SenderName,SentBy,SentTo, received,SentDate,DateReceived,messge);
        logger.info("-----------------------------------------------Email_1 Subject |{}| Email_1 Body |{}| ", emailSubject, emailBody);


    }

    public static void generateEmailReport(String from, String to, boolean response, String sentBy, String dateSent) {


        EmailDeliveryReports emailDeliveryReports = new EmailDeliveryReports(from, to, response, sentBy, dateSent);
        emailDeliveryReports.save();


    }
    public static void emailreporting(
        String type,String SenderName,String SentBy,
        String SentTo,boolean received,
        String SentDate,String DateReceived,String message  )
    {
int sub=10;
if(message.length()<30){
    sub=message.length();
}
else if(message.length()>30){
    sub=30;
}
EmailReports emr=new EmailReports();
emr.type=type;
emr.SenderName=SenderName;
emr.SentBy=SentBy;
emr.SentTo=SentTo;
emr.received=received;
emr.SentDate=SentDate;
emr.DateReceived=DateReceived;
emr.message=message.substring(0,sub);
emr.save();

}
public static String datestring(){
java.util.Date dt =new java.util.Date();
String date=dt.toString();
    return date;
}

}


