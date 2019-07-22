package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.ebean.Ebean;
import models.Branch;
import models.Phones;
import modules.email.SendEmail;
import modules.sms.SendSms;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.concurrent.HttpExecution;
import play.mvc.*;
import views.html.businesscategory.businesscategory;

import javax.inject.Inject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import static modules.ExcelDataConfig.*;

public class BusinessCategoryController extends Controller {

    private EsbExecutionContext esbExecutionContext;
    private final Logger.ALogger logger = Logger.of(this.getClass());
    private LocalDate currentDate = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");

    public static String SENDER_ID;
    public static String senderIdUsername;
    public static String senderIdPassword;
    public static String SMSbody;


    @Inject
    private FormFactory formFactory;

    @Inject
    public BusinessCategoryController(EsbExecutionContext esbExecutionContext, FormFactory formFactory) {
        this.esbExecutionContext = esbExecutionContext;
        this.formFactory = formFactory;
    }


   // @Security.Authenticated(Secured.class)
    public Result showPhones() {

        Form<FormDataController> phones = formFactory.form(FormDataController.class);

        return ok(businesscategory.render(phones));
    }

    @BodyParser.Of(MyMultipartFormDataBodyParserController.class)
    //@SubjectPresent
    //@Pattern("branch.create")
    public CompletionStage<Result> upload() {

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


        return CompletableFuture.completedFuture(redirect(routes.BusinessCategoryController.showPhones()));
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

            SendSms.sendSMSToPhones(SENDER_ID, senderIdUsername, senderIdPassword, SMSbody);


            String subject = "RE: HEALTH CHECK " + "\n";
            String body = session().get("Username") + " Sent bulk SMS:  on " + HeadOfficeController.dateTimeFormatter.format(HeadOfficeController.currentDateTime) + " and received \t RESPONSE: " + result.get("result");
            SendEmail.sendHealthCheckEmail(subject, body);

            logger.info("+++++++++++++++++++++++++++++++++++++++BULK SMS |{}|", SMSbody);
        }
        return CompletableFuture.completedFuture(ok(result));
    }


    public CompletionStage<Result> postDeleteSelected() {
        //DynamicForm requestData = formFactory.form().bindFromRequest();
        // String Id = requestData.get("Id");

        List<Phones> selected = Phones.finder.query().where().eq("phoneIsSelected", Boolean.TRUE).findList();

        Ebean.beginTransaction();
        Ebean.deleteAll(selected);
        Ebean.commitTransaction();

        if (selected == null) {
            //logger.info("The Requested User is Null+=== User++++{}++++ Creator +++++{}", requestData.get("id"), session("Username"));
            return CompletableFuture.completedFuture(notFound());
        }
        Ebean.deleteAll(selected);
        // logger.info("The Requested User has been successfully Deleted UserNumber++++{}++++ DeletedBy +++++{}", requestData.get("mobile_number"), session("Username"));

        return CompletableFuture.completedFuture(redirect(routes.BusinessCategoryController.showPhones()));
    }

    public CompletionStage<Result> postDeleteAll() {
        try {

            List<Phones> allPhones = Phones.finder.all();
            Ebean.beginTransaction();
            Ebean.deleteAll(allPhones);
            Ebean.commitTransaction();

            if (allPhones == null) {
                ////  logger.info("The Requested User is Null+=== User++++{}++++ Creator +++++{}", requestData.get("id"), session("Username"));
                return CompletableFuture.completedFuture(notFound());
            }
            // logger.info("The Requested User has been successfully Deleted UserNumber++++{}++++ DeletedBy +++++{}", requestData.get("mobile_number"), session("Username"));

        } catch (Exception e) {
            // Ebean.endTransaction();
            e.printStackTrace();

        }

        return CompletableFuture.completedFuture(redirect(routes.BusinessCategoryController.showPhones()));
    }


    public CompletionStage<Result> generateExcelReport() {
        try {
            List<Phones> phonesList = Phones.finder.all().subList(1, 5);


            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("IndividualPhoneNumbers");

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

            for (Phones phone : phonesList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(phone.individual_phone);
                row.createCell(1).setCellValue(phone.getIndividualPhone_status());
                row.createCell(2).setCellValue(phone.getIndividualPhone_Comments());

            }

            // Resize all columns to fit the content size
            for (int i = 0; i < sheetColumns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the output to a file
            FileOutputStream fileOut = null;

            fileOut = new FileOutputStream("C:\\Users\\user\\Downloads\\CompanyDirectory.xlsx");

            workbook.write(fileOut);
            fileOut.close();

            return CompletableFuture.completedFuture(redirect(routes.BranchesController.showBranches()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(redirect(routes.BranchesController.showBranches()));

    }
    public Result saveJsGridPhones() {
        Form<Phones> phonesForm = formFactory.form(Phones.class).bindFromRequest();

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Phones phone = phonesForm.get();
        phone.setCreatedBy(session().get("Username"));
       // phone.setPhoneIsSelected(Boolean.FALSE);
        phone.setCreateDate(currentDate.format(formatter));

        phone.save();

        return ok();
    }

    public CompletionStage<Result> postDelete() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String Id = requestData.get("id");

        Phones phones = Phones.finder.byId(Integer.parseInt(Id));
        if (phones == null) {
            logger.info("The Requested User is Null+=== User++++{}++++ Creator +++++{}", requestData.get("id"), session("Username"));
            return CompletableFuture.completedFuture(notFound());
        }
        phones.delete();
        logger.info("The Requested User has been successfully Deleted UserNumber++++{}++++ DeletedBy +++++{}", requestData.get("mobile_number"), session("Username"));

        return CompletableFuture.completedFuture(ok());
    }

    public Result postEditPhones() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String Id = requestData.get("id");
        String individual_phone = requestData.get("individual_phone");
        String individualPhone_status = requestData.get("individualPhone_status");
        String individualPhone_Comments = requestData.get("individualPhone_Comments");


        String phoneIsSelected = requestData.get("phoneIsSelected");
        String createdby = session().get("Username");

        logger.info("####################################################APPROVED STATUS {} ", phoneIsSelected);

        Phones oldPhones = Phones.finder.byId(Integer.parseInt(Id));

        if (oldPhones == null) {
            return notFound("Not Found");
        }
        oldPhones.setIndividual_phone(individual_phone);
        oldPhones.setIndividualPhone_status(individualPhone_status);
        oldPhones.setIndividualPhone_Comments(individualPhone_Comments);
        //oldPhones.setPhoneIsSelected(Boolean.getBoolean(phoneIsSelected));
        oldPhones.setCreatedBy(createdby);
        oldPhones.setCreateDate(currentDate.format(formatter));
        oldPhones.save();
        return ok();

    }


    public CompletionStage<Result> loadPhones() {

        Executor myEc = HttpExecution.fromThread((Executor) esbExecutionContext);

        logger.info("Loading businesses....for user {} and Branch {} ", session().get("Username"), session().get("branch"));

        return QueryPhones().thenApplyAsync(businessCategories -> ok(Json.toJson(businessCategories)), myEc);
    }


    private CompletionStage<List<Phones>> QueryPhones() {
        List<Phones> phonesList = Phones.finder.all();

        return CompletableFuture.completedFuture(phonesList);
    }

}
