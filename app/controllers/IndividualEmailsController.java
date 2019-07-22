package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.ebean.Ebean;
import models.*;
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
import views.html.persons.corporate_emails;
import views.html.persons.individual_emails;

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

public class IndividualEmailsController extends Controller {


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

    private static List<IndividualEmails> individualEmailsList;


    @Inject
    public static FormFactory formFactory;

    @Inject
    public IndividualEmailsController(EsbExecutionContext esbExecutionContext, FormFactory formFactory) {
        this.esbExecutionContext = esbExecutionContext;
        this.formFactory = formFactory;

    }

    //  @SubjectPresent
    //@Pattern("branch.create")
    public Result showIndividualEmails() {
        Form<FormDataController> individualEmails = formFactory.form(FormDataController.class);
        Form<Message> emailForm = formFactory.form(Message.class);

        return ok(individual_emails.render(individualEmails, emailForm));
    }


    @BodyParser.Of(MyMultipartFormDataBodyParserController.class)
    //@SubjectPresent
    //@Pattern("branch.create")
    public CompletionStage<Result> uploadIndividualEmails() {

        final Http.MultipartFormData<File> formData = request().body().asMultipartFormData();
        final Http.MultipartFormData.FilePart<File> filePart = formData.getFile("name");
        final File file = filePart.getFile();

        String createdBy = session().get("Username");
        String dateCreated = HeadOfficeController.currentDateAndTime;
        try {

            importExcelIndividualEmails(file, createdBy, dateCreated);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return CompletableFuture.completedFuture(redirect(routes.IndividualEmailsController.showIndividualEmails()));
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
    public CompletionStage<Result> saveJsGridIndividualEmails() {
        ObjectNode result = Json.newObject();
        Form<IndividualEmails> individualEmailsForm = formFactory.form(IndividualEmails.class).bindFromRequest();
        IndividualEmails individualEmails = individualEmailsForm.get();

        recordCount = IndividualEmails.finder.query().where().eq("individualEmail", individualEmails.individualEmail).findCount();

        individualEmails.setSelected(Boolean.FALSE);


        if (recordCount > 0) {

            responseCode = "304";
            responseMsg = "Sorry! this number : " + individualEmails.individualEmail + " already exists in the Database!";

            result.put("responseCode", responseCode)
                    .put("responseMsg", responseMsg);
            logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++ Response |{}|", responseMsg);

            return CompletableFuture.completedFuture(ok(result));

        } else {

            individualEmails.setCreatedBy(session().get("Username"));
            individualEmails.setDateCreated(HeadOfficeController.currentDateAndTime);
            individualEmails.save();
            return CompletableFuture.completedFuture(ok());
        }
    }

    public CompletionStage<Result> postSaveIndividualEmails() {

        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();

        logger.info("######################################## {}", json.toString());

        String Email = json.get("Email_1").asText();
        String Description = json.get("Description").asText();
        String comments = json.get("Comments").asText();

        String createdBy = session().get("Username");


        recordCount = Branch.finder.query().where().eq("individualEmail", Email).findCount();
        if (recordCount > 0) {

            responseCode = "304";
            responseMsg = "Record exists in the Database!";

            result.put("responseCode", responseCode)
                    .put("responseMsg", responseMsg);

            logger.info("+++++++++++++++++++++++++++++++++++++ Response |{}| ", result.toString());

            return CompletableFuture.completedFuture(ok(result));

        }


        String dateCreated = HeadOfficeController.currentDateAndTime;

        IndividualEmails individualEmails = new IndividualEmails(Email, Description,
                comments, createdBy, dateCreated);

        individualEmails.save();

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
    public CompletionStage<Result> editIndividualEmails() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String Id = requestData.get("emailId");
        String Email = requestData.get("individualEmail");
        String Description = requestData.get("individualDescription");
        String comments = requestData.get("individualComments");
        String selected = requestData.get("individualSelected");

        logger.info("####################################################APPROVED STATUS {} ", selected);

        IndividualEmails individualEmails = IndividualEmails.finder.byId(Integer.valueOf(Id));
        if (individualEmails == null) {
            return CompletableFuture.completedFuture(notFound("Not Found"));
        }

        logger.info("####################################################Old Profile{} ", individualEmails.individualEmail);

        individualEmails.setEmail(Email);
        individualEmails.setDescription(Description);
        individualEmails.setComments(comments);
        individualEmails.setSelected(Boolean.parseBoolean(selected));

        individualEmails.setCreatedBy(session().get("Username"));
        individualEmails.setDateCreated(HeadOfficeController.currentDateAndTime);

        individualEmails.save();
        return CompletableFuture.completedFuture(ok());
    }

    // @Security.Authenticated(Secured.class)
    public CompletionStage<Result> loadIndividualEmails() {

        Executor myEc = HttpExecution.fromThread((Executor) esbExecutionContext);

        logger.info("Loading branches....for user {} and Branch {} ", session().get("Username"), session().get("branch"));

        return QueryIndividualEmails().thenApplyAsync(individualPhoneNumbers -> ok(Json.toJson(individualPhoneNumbers)), myEc);
    }


    public static CompletionStage<List<IndividualEmails>> QueryIndividualEmails() {

        String userRoleName = session().get("RoleName");
        int count = IndividualEmails.finder.all().size();

/*
        if (count != 0) {
            if (!userRoleName.equals("user")) {

                branches = Branch.finder.all().subList(0, 100);

            }

        }
*/
        individualEmailsList = IndividualEmails.finder.all();
        logger.info("+++++++++++++++++++++++++++++++++++++++++++ RoleName |{}|", userRoleName);

        return CompletableFuture.completedFuture(individualEmailsList);

    }


    public CompletionStage<Result> postDeleteIndividualEmails() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String Id = requestData.get("emailId");

        IndividualEmails individualEmails = IndividualEmails.finder.byId(Integer.parseInt(Id));
        if (individualEmails == null) {
            logger.info("The Requested User is Null+=== User++++{}++++ Creator +++++{}", requestData.get("id"), session("Username"));
            return CompletableFuture.completedFuture(notFound());
        }
        individualEmails.delete();
        logger.info("The Requested User has been successfully Deleted UserNumber++++{}++++ DeletedBy +++++{}", requestData.get("mobile_number"), session("Username"));

        return CompletableFuture.completedFuture(ok());
    }

    public CompletionStage<Result> postDeleteSelectedIndividualEmails() {

        List<IndividualEmails> individualEmails = IndividualEmails
                .finder.query().where().eq("individualSelected", Boolean.TRUE).findList();

        Ebean.beginTransaction();
        Ebean.deleteAll(individualEmails);
        Ebean.commitTransaction();

        if (individualEmails == null) {
            return CompletableFuture.completedFuture(notFound());
        }
        Ebean.deleteAll(individualEmails);
        // logger.info("The selectedLeaders User has been successfully Deleted UserNumber++++{}++++ DeletedBy +++++{}", requestData.get("mobile_number"), session("Username"));

        return CompletableFuture.completedFuture(redirect(routes.IndividualEmailsController.showIndividualEmails()));
    }

    public CompletionStage<Result> postDeleteAllIndividualEmails() {

        try {

            List<IndividualEmails> individualEmails = IndividualEmails.finder.all();
            Ebean.beginTransaction();
            Ebean.deleteAll(individualEmails);
            Ebean.commitTransaction();


        } catch (Exception e) {
            // Ebean.endTransaction();

            e.printStackTrace();

        }

        return CompletableFuture.completedFuture(redirect(routes.IndividualEmailsController.showIndividualEmails()));
    }


    public CompletionStage<Result> generateExcelReport() {
        try {
            List<IndividualEmails> individualEmails = IndividualEmails.finder.all().subList(1, 5);


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

            for (IndividualEmails email : individualEmailsList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(email.individualEmail);
                row.createCell(1).setCellValue(email.individualDescription);
                row.createCell(2).setCellValue(email.individualComments);

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

            return CompletableFuture.completedFuture(redirect(routes.IndividualEmailsController.showIndividualEmails()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(redirect(routes.IndividualEmailsController.showIndividualEmails()));

    }

}
