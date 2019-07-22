package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.ebean.Ebean;
import models.Branch;
import models.HeadOffice;
import models.IndividualPhoneNumbers;
import models.Message;
import modules.email.SendEmail;
import modules.sms.SendSms;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.NonUniqueResultException;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.concurrent.HttpExecution;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.persons.individual_phone_numbers;

import javax.inject.Inject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import static modules.ExcelDataConfig.*;

public class IndividualPhoneNumberController extends Controller {

    private EsbExecutionContext esbExecutionContext;
    public static Logger.ALogger logger = Logger.of(BranchesController.class);

    public static int recordCount;
    public static String responseMsg;
    public static String responseCode;
    public static String from;
    public static String emailPassword;
    public static String subject;
    public static String body;

    public static String SENDER_ID;
    public static String senderIdUsername;
    public static String senderIdPassword;
    public static String SMSbody;

    private SendEmail sendEmail;
    private Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");

  //  private static List<IndividualPhoneNumbers> individualPhoneNumbers;


    @Inject
    public static FormFactory formFactory;

    @Inject
    public IndividualPhoneNumberController(EsbExecutionContext esbExecutionContext, FormFactory formFactory) {
        this.esbExecutionContext = esbExecutionContext;
        this.formFactory = formFactory;

    }

    //  @SubjectPresent
    //@Pattern("branch.create")
    public CompletionStage<Result> showPhoneNumbers() {

        Form<FormDataController> individualPhoneNumbers = formFactory.form(FormDataController.class);
        Form<Message> phoneEmailForm = formFactory.form(Message.class);

        return CompletableFuture.completedFuture(ok(individual_phone_numbers.render(individualPhoneNumbers, phoneEmailForm)));
    }


    @BodyParser.Of(MyMultipartFormDataBodyParserController.class)
    //@SubjectPresent
    //@Pattern("branch.create")
    public CompletionStage<Result> uploadIndividualPhoneNumbers() {

        final Http.MultipartFormData<File> formData = request().body().asMultipartFormData();
        final Http.MultipartFormData.FilePart<File> filePart = formData.getFile("name");
        final File file = filePart.getFile();

        String createdBy = session().get("Username");
        String dateCreated = HeadOfficeController.currentDateAndTime;
        try {

            importExcelIndividualPhoneNumbers(file, createdBy, dateCreated);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return CompletableFuture.completedFuture(redirect(routes.IndividualPhoneNumberController.showPhoneNumbers()));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> sendBulkSMS() {
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();

        SENDER_ID = json.get("senderIDTextField").asText();
        senderIdUsername = json.get("userNameTextField").asText();
        senderIdPassword = json.get("senderIDPasswordTextField").asText();
        SMSbody = json.get("smsbodyTextField").asText();

        if (SMSbody.equals(null) || SENDER_ID.equals(null) || senderIdUsername.equals(null) || senderIdPassword.equals(null)) {

            result.put("result", "empty");

            return CompletableFuture.completedFuture(ok(result));

        } else {
            result.put("result", "Success!");

            SendSms.sendSMS(SENDER_ID, senderIdUsername, senderIdPassword, SMSbody);


            String subject = "RE: HEALTH CHECK " + "\n";
            String body = session().get("Username") + " Sent bulk SMS:  on " + HeadOfficeController.dateTimeFormatter.format(HeadOfficeController.currentDateTime) + " and received \t RESPONSE: " + result.get("result");
            SendEmail.sendHealthCheckEmail(subject, body);

            logger.info("+++++++++++++++++++++++++++++++++++++++BULK SMS |{}|", SMSbody);
        }
        return CompletableFuture.completedFuture(ok(result));
    }


    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> sendEmail() {

        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();

        from = json.get("fromTextField").asText();
        emailPassword = json.get("passwordTextField").asText();
        subject = json.get("subjectTextField").asText();
        body = json.get("bodyTextField").asText();

        if (subject.equals(null) || body.equals(null) || from.equals(null) || emailPassword.equals(null)) {

            result.put("result", "subject or body is empty");

            return CompletableFuture.completedFuture(ok(result));
        }

        result.put("result", "Successful!");

        sendEmail = new SendEmail();
        sendEmail.sendBulkEmail(from, emailPassword, subject, body);

        logger.info("-----------------------------------------------Subject |{}| Body |{}|", subject, body);

        return CompletableFuture.completedFuture(redirect(routes.BranchesController.showBranches()));
    }

    private long operateOnTempFile(File file) throws IOException {
        final long size = Files.size(file.toPath());
        Files.deleteIfExists(file.toPath());
        return size;
    }

    //  @SubjectPresent
    //@Pattern("branch.edit")
    public CompletionStage<Result> saveJsGridIndividualPhoneNumbers() {
        ObjectNode result = Json.newObject();
        Form<IndividualPhoneNumbers> individualPhoneNumbersForm = formFactory.form(IndividualPhoneNumbers.class).bindFromRequest();
        IndividualPhoneNumbers individualPhoneNumbers = individualPhoneNumbersForm.get();
/*
        recordCount = IndividualPhoneNumbers.finder.query().where().eq("individual_phone", individualPhoneNumbers.getIndividual_Phone_Number()).findCount();

        individualPhoneNumbers.setPhone_selected(Boolean.FALSE);


        if (recordCount > 0) {

            responseCode = "304";
            responseMsg = "Sorry! this number : " + individualPhoneNumbers.individual_Phone_Number + " already exists in the Database!";

            result.put("responseCode", responseCode)
                    .put("responseMsg", responseMsg);
            logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++ Response |{}|", responseMsg);

            return CompletableFuture.completedFuture(ok(result));

        } else {
*/
            individualPhoneNumbers.setIndividualPhone_CreatedBy(session().get("Username"));
            individualPhoneNumbers.setIndividualPhone_dateCreated(HeadOfficeController.currentDateAndTime);
            individualPhoneNumbers.save();
            return CompletableFuture.completedFuture(ok());
       // }
    }

    public CompletionStage<Result> postSaveIndividualPhoneNumbers() {

        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();

        logger.info("######################################## {}", json.toString());

        String Phone_Number = json.get("Phone_Number").asText();
        String Status = json.get("Status").asText();
        String comments = json.get("Comments").asText();

        String createdBy = session().get("Username");


        recordCount = Branch.finder.query().where().eq("Phone_Number", Phone_Number).findCount();
        if (recordCount > 0) {

            responseCode = "304";
            responseMsg = "Record exists in the Database!";

            result.put("responseCode", responseCode)
                    .put("responseMsg", responseMsg);

            logger.info("+++++++++++++++++++++++++++++++++++++ Response |{}| ", result.toString());

            return CompletableFuture.completedFuture(ok(result));

        }


        String dateCreated = HeadOfficeController.currentDateAndTime;
/*
        IndividualPhoneNumbers individualPhoneNumbers = new IndividualPhoneNumbers(Phone_Number, Status,
                comments, createdBy, dateCreated);

        individualPhoneNumbers.save();
*/
        responseCode = "200";
        responseMsg = "Saved Successfully";

        result.put("responseCode", responseCode);
        result.put("responseMsg", responseMsg);

        logger.info("+++++++++++++++++++++++++++++++++++++ Response |{}| ", result.toString());

        return CompletableFuture.completedFuture(ok(result));

    }

    public CompletionStage<Result> returnLeaderNameSuggestions() {
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();

        // String userName = json.get("User").asText();
        String businessName = json.get("Full_Names").asText();

        List<String> nameSuggestions = Branch.finder.query().where()
                .contains("Full_Names", businessName)
                .select("Full_Names")
                .findSingleAttributeList();

        responseCode = "200";
        responseMsg = "success";

        result.put("responseCode", responseCode)
                .put("responseMsg", responseMsg)
                .put("suggestions", nameSuggestions.toString());

        return CompletableFuture.completedFuture(ok(result));

    }

    public CompletionStage<Result> returnLeaderData() {
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();

        try {

            String userName = json.get("User").asText();
            String businessName = json.get("Full_Names").asText();

            //
            // You need to add the maven dependency for avaje-ebeanorm-elastic for 'matching' commented below
            Branch company = Branch.finder.query().where().contains("Company_Name", businessName).findOne();

            //Branch company = Branch.finder.query().where().contains("Company_Name", businessName).findOne();

            if (company == null) {

                responseCode = "404";
                responseMsg = "Sorry, Record Not Found";

            } else {

                responseCode = "200";
                responseMsg = "Congratulations!";

                result.put("responseCode", responseCode)
                        .put("responseMsg", responseMsg)

                        .put("Company_Name", company.getCompany_Name())
                        .put("Company_Category", company.getCompany_Category())
                        .put("Company_Subcategory", company.getCompany_Subcategory())
                        .put("Email_1", company.getEmail_1())
                        .put("Email_2", company.getEmail_2())
                        .put("Phone_1", company.getPhone_1())
                        .put("Phone_2", company.getPhone_2())
                        .put("Website", company.getWebsite())
                        .put("County", company.getCounty())
                        .put("Town", company.getTown())
                        .put("Street_Name", company.getStreet_Name())
                        .put("Building", company.getBuilding())
                        .put("MapLatitude", company.getMapLatitude())
                        .put("MapLongitude", company.getMapLongitude())
                        .put("companyBranch", company.getCompany_Branch())
                        .put("Services", company.getServices())
                        .put("Status", company.getStatus())
                        .put("Comments", company.getComments());

            }

            String subject = "RE: HEALTH CHECK " + "\n";
            String body = userName + " Searched for Company Name: " + businessName + "on " + HeadOfficeController.dateTimeFormatter.format(HeadOfficeController.currentDateTime) + " and received \t RESPONSE: " + responseMsg;
            SendEmail.sendHealthCheckEmail(subject, body);

            logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++Email_1 |{}|", subject + body);

            logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++Response |{}|", responseMsg);
        } catch (NullPointerException ex) {
            ex.printStackTrace();

        } catch (NonUniqueResultException ex) {

            responseCode = "405";
            responseMsg = "Sorry, Please narrow down your Quick Search";

            result.put("responseCode", responseCode)
                    .put("responseMsg", responseMsg);
        }

        return CompletableFuture.completedFuture(ok(result));
    }


    //@SubjectPresent
    // @Pattern("branch.edit.approve")
    public CompletionStage<Result> editIndividualPhoneNumbers() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String Id = requestData.get("Id");
        String Phone_Number = requestData.get("individual_phone");
        String Status = requestData.get("individualPhone_status");
        String comments = requestData.get("individualPhone_Comments");
        String selected = requestData.get("individualPhone_selected");

        logger.info("####################################################APPROVED STATUS {} ", selected);

        IndividualPhoneNumbers existingIndividualPhoneNumbers = IndividualPhoneNumbers.finder.byId(Integer.valueOf(Id));
        if (existingIndividualPhoneNumbers == null) {
            return CompletableFuture.completedFuture(notFound("Not Found"));
        }

        logger.info("####################################################Old Profile{} ", existingIndividualPhoneNumbers.getIndividual_phone());

        existingIndividualPhoneNumbers.setIndividual_phone(Phone_Number);
        existingIndividualPhoneNumbers.setIndividualPhone_status(Status);
        existingIndividualPhoneNumbers.setIndividualPhone_Comments(comments);
        existingIndividualPhoneNumbers.setIndividualPhone_selected(Boolean.parseBoolean(selected));

        existingIndividualPhoneNumbers.setIndividualPhone_CreatedBy(session().get("Username"));
        existingIndividualPhoneNumbers.setIndividualPhone_dateCreated(HeadOfficeController.currentDateAndTime);

        existingIndividualPhoneNumbers.save();
        return CompletableFuture.completedFuture(ok());
    }

    // @Security.Authenticated(Secured.class)
    public CompletionStage<Result> loadIndividualPhoneNumbers() {

        Executor myEc = HttpExecution.fromThread((Executor) esbExecutionContext);

        logger.info("Loading branches....for user {} and Branch {} ", session().get("Username"), session().get("branch"));

        return QuerydividualPhoneNumbers().thenApplyAsync(individualPhoneNumbers -> ok(Json.toJson(individualPhoneNumbers)), myEc);
    }


    public static CompletionStage<List<IndividualPhoneNumbers>> QuerydividualPhoneNumbers() {

        String userRoleName = session().get("RoleName");
        int count = IndividualPhoneNumbers.finder.all().size();

        List<IndividualPhoneNumbers> individualPhoneNumbers = IndividualPhoneNumbers.finder.all();

        logger.info("+++++++++++++++++++++++++++++++++++++++++++ RoleName |{}|", userRoleName);

        return CompletableFuture.completedFuture(individualPhoneNumbers);

    }


    public CompletionStage<Result> postDeleteIndividualPhoneNumbers() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String Id = requestData.get("Id");
        IndividualPhoneNumbers individualPhoneNumbers = IndividualPhoneNumbers.finder.byId(Integer.parseInt(Id));
        if (individualPhoneNumbers == null) {
            logger.info("The Requested User is Null+=== User++++{}++++ Creator +++++{}", requestData.get("Id"), session("Username"));
            return CompletableFuture.completedFuture(notFound());
        }
        individualPhoneNumbers.delete();
        logger.info("The Requested User has been successfully Deleted UserNumber++++{}++++ DeletedBy +++++{}", requestData.get("mobile_number"), session("Username"));

        return CompletableFuture.completedFuture(ok());
    }

    public CompletionStage<Result> postDeleteSelectedPhoneNumbers() {


        List<IndividualPhoneNumbers> selectedIndividualPhoneNumbers = IndividualPhoneNumbers.finder.query().where().eq("phone_selected", Boolean.TRUE).findList();

        Ebean.beginTransaction();
        Ebean.deleteAll(selectedIndividualPhoneNumbers);
        Ebean.commitTransaction();

        if (selectedIndividualPhoneNumbers == null) {
            //logger.info("The Requested User is Null+=== User++++{}++++ Creator +++++{}", requestData.get("id"), session("Username"));
            return CompletableFuture.completedFuture(notFound());
        }
        Ebean.deleteAll(selectedIndividualPhoneNumbers);
        // logger.info("The selectedLeaders User has been successfully Deleted UserNumber++++{}++++ DeletedBy +++++{}", requestData.get("mobile_number"), session("Username"));

        return CompletableFuture.completedFuture(redirect(routes.IndividualPhoneNumberController.showPhoneNumbers()));
    }

    public CompletionStage<Result> postDeleteAllPhoneNumbers() {
        try {

            List<IndividualPhoneNumbers> individualPhoneNumbers = IndividualPhoneNumbers.finder.all();
            Ebean.beginTransaction();
            Ebean.deleteAll(individualPhoneNumbers);
            Ebean.commitTransaction();

            if (individualPhoneNumbers == null) {
                ////  logger.info("The Requested User is Null+=== User++++{}++++ Creator +++++{}", requestData.get("id"), session("Username"));
                return CompletableFuture.completedFuture(notFound());
            }
            // logger.info("The Requested User has been successfully Deleted UserNumber++++{}++++ DeletedBy +++++{}", requestData.get("mobile_number"), session("Username"));

        } catch (Exception e) {
            // Ebean.endTransaction();
            e.printStackTrace();

        }

        return CompletableFuture.completedFuture(redirect(routes.IndividualPhoneNumberController.showPhoneNumbers()));
    }


    public CompletionStage<Result> generateExcelReport() {
        try {
            List<IndividualPhoneNumbers> individualPhoneNumbers = IndividualPhoneNumbers.finder.all().subList(1, 5);


            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Leaders");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.BRIGHT_GREEN.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Create a Row
            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < sheetColumns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(sheetColumns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            // Create Other rows and cells with contacts data
            int rowNum = 1;

            for (IndividualPhoneNumbers number : individualPhoneNumbers) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(number.getIndividual_phone());
                row.createCell(1).setCellValue(number.getIndividualPhone_status());
                row.createCell(2).setCellValue(number.getIndividualPhone_Comments());

            }

            // Resize all columns to fit the content size
            for (int i = 0; i < sheetColumns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the output to a file
            FileOutputStream fileOut = null;

            fileOut = new FileOutputStream("C:\\Users\\user\\Downloads\\IndividualPhoneNumbers.xlsx");

            workbook.write(fileOut);
            fileOut.close();

            return CompletableFuture.completedFuture(redirect(routes.IndividualPhoneNumberController.showPhoneNumbers()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(redirect(routes.IndividualPhoneNumberController.showPhoneNumbers()));

    }
}
