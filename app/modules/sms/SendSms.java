package modules.sms;

import controllers.BranchesController;
import infobip.api.client.SendSingleTextualSms;
import infobip.api.config.BasicAuthConfiguration;
import infobip.api.model.sms.mt.send.SMSResponse;
import infobip.api.model.sms.mt.send.SMSResponseDetails;
import infobip.api.model.sms.mt.send.textual.SMSTextualRequest;
import models.Branch;
import models.HeadOffice;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import models.SmsReports;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class SendSms extends SMSconfig {
   static String[] params=new String[8];
   static boolean received=false;

    public static void sendSMS(String SENDER_ID, String username, String password, String smsToSend) {
        params[0]="BUSINESS SMS";
        params[1]=username;
        params[2]=SENDER_ID;
        params[3]="BUSINESS SMS";
        params[4]=datestring();
        params[5]=" ";
        params[7]=smsToSend;
      


        //  for (int i =0; i<getMSISDNfromDB().size(); i++) {
        List<String> businessNames = Branch.finder.query().where().eq("selected", Boolean.TRUE).select("Company_Name").findSingleAttributeList();

        SendSingleTextualSms client = new SendSingleTextualSms(new BasicAuthConfiguration(username, password));
        SMSTextualRequest requestBody = new SMSTextualRequest();
        requestBody.setFrom(SENDER_ID);
        requestBody.setTo(getMSISDNfromDB());
        // requestBody.setText(businessNames + "\t" + smsToSend);
        requestBody.setText(smsToSend);

        SMSResponse response = client.execute(requestBody);

        for (SMSResponseDetails sentMessageInfo : response.getMessages()) {
            params[3]= sentMessageInfo.getTo();
            params[6]=sentMessageInfo.getStatus().getName();
            System.out.println("Message ID: " + sentMessageInfo.getMessageId());
            System.out.println("Receiver: " + sentMessageInfo.getTo());
            System.out.println("Message status: " + sentMessageInfo.getStatus().getName());
            System.out.println("------------------------------------------------");

            BranchesController.logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++SMS RESPONSE: |{}|", sentMessageInfo.getStatus());
            reportSms(params);
        }
        //  }

    }

    public static void sendSMSToPhones(String SENDER_ID, String username, String password, String smsToSend) {
       params[0]="INDIVIDUAL SMS";
        params[1]=username;
        params[2]=SENDER_ID;
        params[4]=datestring();
        params[5]=" ";
      
        //  for (int i =0; i<getMSISDNfromDB().size(); i++) {
        List<String> phones = Branch.finder.query().where().eq("phoneIsSelected", Boolean.TRUE).select("individual_phone").findSingleAttributeList();

        SendSingleTextualSms client = new SendSingleTextualSms(new BasicAuthConfiguration(username, password));
        SMSTextualRequest requestBody = new SMSTextualRequest();
        requestBody.setFrom(SENDER_ID);
        requestBody.setTo(phones);
        // requestBody.setText(businessNames + "\t" + smsToSend);
        requestBody.setText(smsToSend);

        SMSResponse response = client.execute(requestBody);

        for (SMSResponseDetails sentMessageInfo : response.getMessages()) {
            params[3]= sentMessageInfo.getTo();
            params[6]=sentMessageInfo.getStatus().getName();
            
            System.out.println("Message ID: " + sentMessageInfo.getMessageId());
            System.out.println("Receiver: " + sentMessageInfo.getTo());
            System.out.println("Message status: " + sentMessageInfo.getStatus().getName());
            System.out.println("------------------------------------------------");
 reportSms(params);
            BranchesController.logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++SMS RESPONSE: |{}|", sentMessageInfo.getStatus());
        }
        //  }

    }
 public static void sendSMSToPersons(String SENDER_ID, String username, String password, String smsToSend) {
        params[0]="SMS TO PERSONS";
        params[1]=username;
        params[2]=SENDER_ID;
        params[4]=datestring();
        params[5]=" ";
        params[7]=smsToSend;
        //  for (int i =0; i<getMSISDNfromDB().size(); i++) {
        List<String> persons = HeadOffice.finder.query().where().eq("selected", Boolean.TRUE).select("Phone_1").findSingleAttributeList();

        SendSingleTextualSms client = new SendSingleTextualSms(new BasicAuthConfiguration(username, password));
        SMSTextualRequest requestBody = new SMSTextualRequest();
        requestBody.setFrom(SENDER_ID);
        requestBody.setTo(persons);
        // requestBody.setText(businessNames + "\t" + smsToSend);
        requestBody.setText(smsToSend);

        SMSResponse response = client.execute(requestBody);

        for (SMSResponseDetails sentMessageInfo : response.getMessages()) {
            params[3]= sentMessageInfo.getTo();
            params[6]=sentMessageInfo.getStatus().getName();
            System.out.println("Message ID: " + sentMessageInfo.getMessageId());
            System.out.println("Receiver: " + sentMessageInfo.getTo());
            System.out.println("Message status: " + sentMessageInfo.getStatus().getName());
            System.out.println("------------------------------------------------");
 reportSms(params);
            BranchesController.logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++SMS RESPONSE: |{}|", sentMessageInfo.getStatus());
        }
        //  }

    }

    public static void smsPersons(String smsToSend) {
        params[0]="BUSINESS SMS";
        params[1]=USERNAME;
        params[2]=FROM;
        params[4]=datestring();
        params[5]=" ";
        params[7]=smsToSend;

        SendSingleTextualSms client = new SendSingleTextualSms(new BasicAuthConfiguration(USERNAME, PASSWORD));
        SMSTextualRequest requestBody = new SMSTextualRequest();
        requestBody.setFrom(FROM);
        requestBody.setTo(getPersonMSISDN());
        // requestBody.setText(businessNames + "\t" + smsToSend);
        requestBody.setText(smsToSend);

        SMSResponse response = client.execute(requestBody);

        for (SMSResponseDetails sentMessageInfo : response.getMessages()) {
            params[3]= sentMessageInfo.getTo();
            params[6]=sentMessageInfo.getStatus().getName();
            System.out.println("Message ID: " + sentMessageInfo.getMessageId());
            System.out.println("Receiver: " + sentMessageInfo.getTo());
            System.out.println("Message status: " + sentMessageInfo.getStatus().getName());
            System.out.println("------------------------------------------------");
            reportSms(params);

            BranchesController.logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++SMS RESPONSE: |{}|", sentMessageInfo.getStatus());
        }
        //  }

    }


    public static void sendAccountCreationSMS(String smsToSend, String msisdn) {
        params[0]="ACCOUNT_CREATION SMS";
        params[1]=USERNAME;
        params[2]=FROM;
        params[4]=datestring();
        params[5]=" ";
        params[7]=smsToSend;

        SendSingleTextualSms client = new SendSingleTextualSms(new BasicAuthConfiguration(USERNAME, PASSWORD));
        SMSTextualRequest requestBody = new SMSTextualRequest();
        requestBody.setFrom(FROM);
        requestBody.setTo(Arrays.asList(msisdn));
        requestBody.setText(smsToSend);

        SMSResponse response = client.execute(requestBody);

        for (SMSResponseDetails sentMessageInfo : response.getMessages()) {
            params[3]= sentMessageInfo.getTo();
            params[6]=sentMessageInfo.getStatus().getName();
            System.out.println("Message ID: " + sentMessageInfo.getMessageId());
            System.out.println("Receiver: " + sentMessageInfo.getTo());
            System.out.println("Message status: " + sentMessageInfo.getStatus().getName());
            System.out.println("------------------------------------------------");
            reportSms(params);

            BranchesController.logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++SMS RESPONSE: |{}|", sentMessageInfo.getStatus());
        }
    }

    public static List<String> getMSISDNfromDB() {

        List<String> msisdnList = Branch.finder.query().where().eq("selected", Boolean.TRUE).select("Phone_2").findSingleAttributeList();


        BranchesController.logger.info("-----------------------------------------------MSISDN | {} |", msisdnList);

        return msisdnList;


    }

    public static List<String> getPersonMSISDN() {

        // List<String> msisdnList = Branch.finder.query().where().eq("selected", Boolean.TRUE).select("Phone_1").findSingleAttributeList();
        List<String> msisdnList = HeadOffice.finder.query().where().contains("Full_Names", "A").select("Phone_1").findSingleAttributeList();


        BranchesController.logger.info("-----------------------------------------------MSISDN | {} |", msisdnList);

        return msisdnList;


    }
public static void reportSms(String[] params){
//PENDING_ENROUTE
boolean received=false;
String type=params[0];
String SenderName=params[1];
String SentBy=params[2];
String SentTo=params[3];
String SentDate=params[4];
String DateReceived=params[5];
String rc=params[6];
String message=params[7];
if(rc.contains("PENDING_ENROUTE")){
received=true;
DateReceived=datestring();
}
SmsReports sms=new SmsReports(type,SenderName,SentBy,SentTo,received,SentDate,DateReceived,message);
sms.save();
    }
    public static String datestring(){
java.util.Date dt =new java.util.Date();
String date=dt.toString();
    return date;
}
}
