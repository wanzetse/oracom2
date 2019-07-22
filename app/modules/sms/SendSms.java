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

public class SendSms extends SMSconfig {

    public static void sendSMS(String SENDER_ID, String username, String password, String smsToSend) {

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
            System.out.println("Message ID: " + sentMessageInfo.getMessageId());
            System.out.println("Receiver: " + sentMessageInfo.getTo());
            System.out.println("Message status: " + sentMessageInfo.getStatus().getName());
            System.out.println("------------------------------------------------");

            BranchesController.logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++SMS RESPONSE: |{}|", sentMessageInfo.getStatus());
        }
        //  }

    }

    public static void sendSMSToPhones(String SENDER_ID, String username, String password, String smsToSend) {

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
            System.out.println("Message ID: " + sentMessageInfo.getMessageId());
            System.out.println("Receiver: " + sentMessageInfo.getTo());
            System.out.println("Message status: " + sentMessageInfo.getStatus().getName());
            System.out.println("------------------------------------------------");

            BranchesController.logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++SMS RESPONSE: |{}|", sentMessageInfo.getStatus());
        }
        //  }

    }
 public static void sendSMSToPersons(String SENDER_ID, String username, String password, String smsToSend) {

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
            System.out.println("Message ID: " + sentMessageInfo.getMessageId());
            System.out.println("Receiver: " + sentMessageInfo.getTo());
            System.out.println("Message status: " + sentMessageInfo.getStatus().getName());
            System.out.println("------------------------------------------------");

            BranchesController.logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++SMS RESPONSE: |{}|", sentMessageInfo.getStatus());
        }
        //  }

    }

    public static void smsPersons(String smsToSend) {

        SendSingleTextualSms client = new SendSingleTextualSms(new BasicAuthConfiguration(USERNAME, PASSWORD));
        SMSTextualRequest requestBody = new SMSTextualRequest();
        requestBody.setFrom(FROM);
        requestBody.setTo(getPersonMSISDN());
        // requestBody.setText(businessNames + "\t" + smsToSend);
        requestBody.setText(smsToSend);

        SMSResponse response = client.execute(requestBody);

        for (SMSResponseDetails sentMessageInfo : response.getMessages()) {
            System.out.println("Message ID: " + sentMessageInfo.getMessageId());
            System.out.println("Receiver: " + sentMessageInfo.getTo());
            System.out.println("Message status: " + sentMessageInfo.getStatus().getName());
            System.out.println("------------------------------------------------");

            BranchesController.logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++SMS RESPONSE: |{}|", sentMessageInfo.getStatus());
        }
        //  }

    }


    public static void sendAccountCreationSMS(String smsToSend, String msisdn) {

        SendSingleTextualSms client = new SendSingleTextualSms(new BasicAuthConfiguration(USERNAME, PASSWORD));
        SMSTextualRequest requestBody = new SMSTextualRequest();
        requestBody.setFrom(FROM);
        requestBody.setTo(Arrays.asList(msisdn));
        requestBody.setText(smsToSend);

        SMSResponse response = client.execute(requestBody);

        for (SMSResponseDetails sentMessageInfo : response.getMessages()) {
            System.out.println("Message ID: " + sentMessageInfo.getMessageId());
            System.out.println("Receiver: " + sentMessageInfo.getTo());
            System.out.println("Message status: " + sentMessageInfo.getStatus().getName());
            System.out.println("------------------------------------------------");

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
}
