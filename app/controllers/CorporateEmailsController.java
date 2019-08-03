package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.ebean.Ebean;
import models.Branch;
import models.CorporateEmails;
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
import views.html.persons.corporate_emails;

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

public class CorporateEmailsController extends Controller {

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
    private static int len;

    private SendEmail sendEmail;
    private Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");

    private static List<CorporateEmails> corporateEmailsList;


    @Inject
    public static FormFactory formFactory;

    @Inject
    public CorporateEmailsController(EsbExecutionContext esbExecutionContext, FormFactory formFactory) {
        this.esbExecutionContext = esbExecutionContext;
        this.formFactory = formFactory;

    }

    //  @SubjectPresent
    //@Pattern("branch.create")
    public Result showCorporateEmails() {
        Form<FormDataController> corporateEmails = formFactory.form(FormDataController.class);
        Form<Message> emailForm = formFactory.form(Message.class);

        return ok(corporate_emails.render(corporateEmails, emailForm));
    }


    @BodyParser.Of(MyMultipartFormDataBodyParserController.class)
    //@SubjectPresent
    //@Pattern("branch.create")
    public CompletionStage<Result> uploadCorporateEmails() {

        final Http.MultipartFormData<File> formData = request().body().asMultipartFormData();
        final Http.MultipartFormData.FilePart<File> filePart = formData.getFile("name");
        final File file = filePart.getFile();

        String createdBy = session().get("Username");
        String dateCreated = HeadOfficeController.currentDateAndTime;
        try {

            importExcelCorporateEmails(file, createdBy, dateCreated);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return CompletableFuture.completedFuture(redirect(routes.CorporateEmailsController.showCorporateEmails()));
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

        if (subject.length()<2 || body.length()<2 || from.length()<7 || emailPassword.length()<5) {

            result.put("result", "empty");

            return CompletableFuture.completedFuture(ok(result));
        }
else{
        
try{
        sendEmail = new SendEmail();
        sendEmail.sendBulkEmail(from, emailPassword, subject, body);

        logger.info("-----------------------------------------------Subject |{}| Body |{}|", subject, body);
result.put("result", "Success!");
}catch(Exception e){
     result.put("result","No Internet Connection");
    e.printStackTrace();
}
}
  return CompletableFuture.completedFuture(ok(result));
   }

    private long operateOnTempFile(File file) throws IOException {
        final long size = Files.size(file.toPath());
        Files.deleteIfExists(file.toPath());
        return size;
    }

    //  @SubjectPresent
    //@Pattern("branch.edit")
    public CompletionStage<Result> saveJsGridCorporateEmails() {
        ObjectNode result = Json.newObject();
        Form<CorporateEmails> corporateEmailsForm = formFactory.form(CorporateEmails.class).bindFromRequest();
        CorporateEmails corporateEmails = corporateEmailsForm.get();

        recordCount = Branch.finder.query().where().eq("Email_1", corporateEmails.getEmail()).findCount();

        corporateEmails.setSelected(Boolean.FALSE);


        if (recordCount > 0) {

            responseCode = "304";
            responseMsg = "Sorry! this number : " + corporateEmails.getEmail() + " already exists in the Database!";

            result.put("responseCode", responseCode)
                    .put("responseMsg", responseMsg);
            logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++ Response |{}|", responseMsg);

            return CompletableFuture.completedFuture(ok(result));

        } else {

            corporateEmails.setCreatedBy(session().get("Username"));
            corporateEmails.setDateCreated(HeadOfficeController.currentDateAndTime);
            corporateEmails.save();
            return CompletableFuture.completedFuture(ok());
        }
    }

    public CompletionStage<Result> postSaveCorporateEmails() {

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

        CorporateEmails corporateEmails = new CorporateEmails(Phone_Number, Status,
                comments, createdBy, dateCreated);

        corporateEmails.save();

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
    public CompletionStage<Result> editCorporateEmails() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String Id = requestData.get("Id");
        String Email = requestData.get("Email_1");
        String Description = requestData.get("Description");
        String comments = requestData.get("Comments");
        String selected = requestData.get("selected");

        logger.info("####################################################APPROVED STATUS {} ", selected);

        CorporateEmails corporateEmails = CorporateEmails.finder.byId(Integer.valueOf(Id));
        if (corporateEmails == null) {
            return CompletableFuture.completedFuture(notFound("Not Found"));
        }

        logger.info("####################################################Old Profile{} ", corporateEmails.getEmail());

        corporateEmails.setEmail(Email);
        corporateEmails.setDescription(Description);
        corporateEmails.setComments(comments);
        corporateEmails.setSelected(Boolean.parseBoolean(selected));

        corporateEmails.setCreatedBy(session().get("Username"));
        corporateEmails.setDateCreated(HeadOfficeController.currentDateAndTime);

        corporateEmails.save();
        return CompletableFuture.completedFuture(ok());
    }

    // @Security.Authenticated(Secured.class)
    public CompletionStage<Result> loadCorporateEmails() {
/*
/oracom/loadCorporateEmails?selected=&Email=&Description=
&Comments=&CreatedBy=&dateCreated=&pageIndex=1&pageSize=50

*/
        //Executor myEc = HttpExecution.fromThread((Executor) esbExecutionContext);
         DynamicForm rq = formFactory.form().bindFromRequest();
        //json object to send back parameters
        ObjectNode node=Json.newObject();
        //array of string to hold received parameters
        String[] params=new String[8];
        params[1]=rq.get("Email");
        params[2]=rq.get("Description");
        params[3]=rq.get("Comments");
        params[4]=rq.get("CreatedBy");
        params[5]=rq.get("dateCreated");
        params[6]=rq.get("pageIndex");
        params[7]=rq.get("pageSize");

        node.put("data",QueryCorporateEmails(params));
        node.put("len",len);


        logger.info("Loading branches....for user {} and Branch {} ", session().get("Username"), session().get("branch"));

    return CompletableFuture.completedFuture(ok(node));
     }


 private  JsonNode QueryCorporateEmails(String[] otherParams) {

        String userRoleName = session().get("RoleName");

/*
/oracom/loadCorporateEmails?selected=&Email=&Description=
&Comments=&CreatedBy=&dateCreated=&pageIndex=1&pageSize=50

*/

       
String Email=otherParams[1];
String Description=otherParams[2];
String Comments=otherParams[3];
String CreatedBy=otherParams[4];
String dateCreated=otherParams[5];
int pageIndex=Integer.parseInt(otherParams[6]);
int pageSize=Integer.parseInt(otherParams[7]);



        len = CorporateEmails.finder.query().where()
        .ilike("Email", "%"+Email+"%")
        .ilike("Description", "%"+Description+"%")
        .ilike("Comments", "%"+Comments+"%")
        .ilike("dateCreated", "%"+dateCreated+"%")
        .findCount();


        corporateEmailsList = CorporateEmails.finder.query().where()
        .ilike("Email", "%"+Email+"%")
        .ilike("Description", "%"+Description+"%")
        .ilike("Comments", "%"+Comments+"%")
        .ilike("dateCreated", "%"+dateCreated+"%")
        .setFirstRow(pageIndex)
        .setMaxRows(pageSize)
        .findPagedList()
        .getList();
        logger.info("+++++++++++++++++++++++++++++++++++++++++++ RoleName |{}|", userRoleName);
        

        return Json.toJson(corporateEmailsList);

    }


    public CompletionStage<Result> postDeleteCorporateEmails() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String Id = requestData.get("Id");

        CorporateEmails corporateEmails = CorporateEmails.finder.byId(Integer.parseInt(Id));
        if (corporateEmails == null) {
            logger.info("The Requested User is Null+=== User++++{}++++ Creator +++++{}", requestData.get("id"), session("Username"));
            return CompletableFuture.completedFuture(notFound());
        }
        corporateEmails.delete();
        logger.info("The Requested User has been successfully Deleted UserNumber++++{}++++ DeletedBy +++++{}", requestData.get("mobile_number"), session("Username"));

        return CompletableFuture.completedFuture(ok());
    }

    public CompletionStage<Result> postDeleteSelectedCorporateEmails() {

        List<CorporateEmails> corporateEmails = CorporateEmails
                .finder.query().where().eq("selected", Boolean.TRUE).findList();

        Ebean.beginTransaction();
        Ebean.deleteAll(corporateEmails);
        Ebean.commitTransaction();

        if (corporateEmails == null) {
            return CompletableFuture.completedFuture(notFound());
        }
        Ebean.deleteAll(corporateEmails);
        // logger.info("The selectedLeaders User has been successfully Deleted UserNumber++++{}++++ DeletedBy +++++{}", requestData.get("mobile_number"), session("Username"));

        return CompletableFuture.completedFuture(redirect(routes.CorporateEmailsController.showCorporateEmails()));
    }

    public CompletionStage<Result> postDeleteAllCorporateEmails() {

        try {

            List<CorporateEmails> corporateEmails = CorporateEmails.finder.all();
            Ebean.beginTransaction();
            Ebean.deleteAll(corporateEmails);
            Ebean.commitTransaction();


        } catch (Exception e) {
            // Ebean.endTransaction();

            e.printStackTrace();

        }

        return CompletableFuture.completedFuture(redirect(routes.CorporateEmailsController.showCorporateEmails()));
    }


    public CompletionStage<Result> generateExcelReport() {
        try {
            List<CorporateEmails> corporateEmails = CorporateEmails.finder.all().subList(1, 5);


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

            for (CorporateEmails email : corporateEmailsList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(email.getEmail());
                row.createCell(1).setCellValue(email.getDescription());
                row.createCell(2).setCellValue(email.getComments());

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

            return CompletableFuture.completedFuture(redirect(routes.CorporateEmailsController.showCorporateEmails()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(redirect(routes.IndividualPhoneNumberController.showPhoneNumbers()));

    }
}
