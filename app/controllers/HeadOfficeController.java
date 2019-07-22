package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import flexjson.JSONSerializer;
import io.ebean.Ebean;
import models.HeadOffice;
import models.Message;
import modules.email.SendEmail;
import modules.sms.SendSms;
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
import views.html.merchants.edit_head_office;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import static modules.ExcelDataConfig.importExcelPersonsData;

public class HeadOfficeController extends Controller {
    private EsbExecutionContext esbExecutionContext;
    private final Logger.ALogger logger = Logger.of(this.getClass());
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    public static LocalDateTime currentDateTime = LocalDateTime.now();
    public static String currentDateAndTime = dateTimeFormatter.format(currentDateTime);
    public static int recordCount;
    public static String responseMsg;
    public static String responseCode;
    public static String subject;
    public static String body;
    public static String SMSbody;
    private SendEmail sendEmail;
    @Inject
    private FormFactory formFactory;

    private Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
    private JSONSerializer postDetailsSerializer = new JSONSerializer();

    @Inject
    public HeadOfficeController(EsbExecutionContext esbExecutionContext, FormFactory formFactory) {
        this.esbExecutionContext = esbExecutionContext;
        this.formFactory = formFactory;
    }

    // @Security.Authenticated(Secured.class)
    public Result showHeadOffice() {

        Form<FormDataController> personsForm = formFactory.form(FormDataController.class);
        Form<Message> emailForm = formFactory.form(Message.class);

        return ok(edit_head_office.render(personsForm, emailForm));
    }

    public CompletionStage<Result> postDeleteSelectedPersons() {

        List<HeadOffice> persons = HeadOffice
                .finder.query().where().eq("selected", Boolean.TRUE).findList();

        Ebean.beginTransaction();
        Ebean.deleteAll(persons);
        Ebean.commitTransaction();

        if (persons == null) {
            return CompletableFuture.completedFuture(notFound());
        }
        Ebean.deleteAll(persons);
        // logger.info("The selectedLeaders User has been successfully Deleted UserNumber++++{}++++ DeletedBy +++++{}", requestData.get("mobile_number"), session("Username"));

        return CompletableFuture.completedFuture(redirect(routes.HeadOfficeController.showHeadOffice()));
    }

    public CompletionStage<Result> postDeleteAllPersons() {

        try {

            List<HeadOffice> persons = HeadOffice.finder.all();
            Ebean.beginTransaction();
            Ebean.deleteAll(persons);
            Ebean.commitTransaction();


        } catch (Exception e) {
            // Ebean.endTransaction();

            e.printStackTrace();

        }

        return CompletableFuture.completedFuture(redirect(routes.HeadOfficeController.showHeadOffice()));
    }

    public Result editHeadOffice() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String Id = requestData.get("Id");
        String company = requestData.get("Company");
        String full_names = requestData.get("Full_Names");
        String email_1 = requestData.get("Email_1");
        String email_2 = requestData.get("Email_2");
        String phone_1 = requestData.get("Phone_1");
        String phone_2 = requestData.get("Phone_2");
        String position = requestData.get("Position");
        String sideHustle = requestData.get("SideHustle");
        String sex = requestData.get("Sex");
        String status = requestData.get("Status");
        String comments = requestData.get("Comments");
        String selected = requestData.get("selected");


        logger.info("####################################################APPROVED STATUS {} ", phone_1);

        HeadOffice person = HeadOffice.finder.byId(Integer.parseInt(Id));
        if (person == null) {
            return notFound("Not Found");
        }

        // logger.info("####################################################Old Profile{} ", headOffice.email);

        person.setCompany(company);
        person.setFull_Names(full_names);
        person.setEmail_1(email_1);
        person.setEmail_2(email_2);
        person.setPhone_1(phone_1);
        person.setPhone_2(phone_2);
        person.setPosition(position);
        person.setSideHustle(sideHustle);
        person.setSex(sex);
        person.setStatus(status);
        person.setComments(comments);
        person.setSelected(Boolean.parseBoolean(selected));

        person.save();

        return ok();
    }

    public Result saveHeadOffice() {
        Form<HeadOffice> personForm = formFactory.form(HeadOffice.class).bindFromRequest();
        HeadOffice person = personForm.get();

        person.setCreatedBy(session().get("Username"));
        person.setDateCreated(currentDateTime.format(dateTimeFormatter));
        person.save();

        return ok();
    }

    public CompletionStage<Result> postDelete() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String Id = requestData.get("Id");

        HeadOffice headOffice = HeadOffice.finder.byId(Integer.parseInt(Id));
        if (headOffice == null) {
            logger.info("The Requested User is Null+=== User++++{}++++ Creator +++++{}", requestData.get("Id"), session("Username"));
            return CompletableFuture.completedFuture(notFound());
        }
        headOffice.delete();
        logger.info("The Requested User has been successfully Deleted UserNumber++++{}++++ DeletedBy +++++{}", requestData.get("mobile_number"), session("Username"));

        return CompletableFuture.completedFuture(ok());
    }

    public CompletionStage<Result> loadHeadOffice() {

        Executor myEc = HttpExecution.fromThread((Executor) esbExecutionContext);

        logger.info("Loading branches....for user {} and Branch {} ", session().get("Username"), session().get("branch"));

        return QueryHeadOffice().thenApplyAsync(persons -> ok(Json.toJson(persons)), myEc);
    }

    private CompletionStage<List<HeadOffice>> QueryHeadOffice() {
        //  List<HeadOffice> persons = HeadOffice.finder.query().where().eq("Deleted", Boolean.FALSE).findList();
        List<HeadOffice> persons = HeadOffice.finder.all();

        return CompletableFuture.completedFuture(persons);
    }

    @BodyParser.Of(MyMultipartFormDataBodyParserController.class)
    //@SubjectPresent
    //@Pattern("branch.create")
    public CompletionStage<Result> upload() {

        final Http.MultipartFormData<File> formData = request().body().asMultipartFormData();
        final Http.MultipartFormData.FilePart<File> filePart = formData.getFile("name");
        final File file = filePart.getFile();


        try {
            String createdby = session().get("Username");
            importExcelPersonsData(file, createdby, dateTimeFormatter.format(currentDateTime));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return CompletableFuture.completedFuture(redirect(routes.HeadOfficeController.showHeadOffice()));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> sendBulkSMS() {
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();

        SMSbody = json.get("smsbodyTextField").asText();

        if (SMSbody.equals(null)) {

            result.put("result", "empty");

            return CompletableFuture.completedFuture(ok(result));

        } else {
            result.put("result", "Success!");

            SendSms.smsPersons(SMSbody);


            String subject = "RE: HEALTH CHECK " + "\n";
            String body = session().get("Username") + " Sent bulk SMS:  on " + dateTimeFormatter.format(currentDateTime) + " and received \t RESPONSE: " + result.get("result");
            SendEmail.sendHealthCheckEmail(subject, body);

            logger.info("+++++++++++++++++++++++++++++++++++++++BULK SMS |{}|", SMSbody);
        }
        return CompletableFuture.completedFuture(ok(result));
    }


    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> sendEmail() {

        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();

        subject = json.get("subjectTextField").asText();
        body = json.get("bodyTextField").asText();

        if (subject.equals(null) || body.equals(null)) {

            result.put("result", "subject or body is empty");
            return CompletableFuture.completedFuture(ok(result));
        }

        result.put("result", "Successful!");

        sendEmail = new SendEmail();
        sendEmail.emailPersons(subject, body);

        logger.info("-----------------------------------------------Subject |{}| Body |{}|", subject, body);

        return CompletableFuture.completedFuture(redirect(routes.HeadOfficeController.showHeadOffice()));
    }

    private long operateOnTempFile(File file) throws IOException {
        final long size = Files.size(file.toPath());
        Files.deleteIfExists(file.toPath());
        return size;
    }

/*
    public Result savejsGrid() {
        DynamicForm requestData = formFactory.form().bindFromRequest();

        String hqName = requestData.get("Full_Names");
        String hqNumber = requestData.get("officeNumber");
        String contact_ps = requestData.get("contactPerson");
        String contact = requestData.get("contactPersonId");
        String mobile_no = requestData.get("mobileNumber");
        String email = requestData.get("emailAddress");
        String location = requestData.get("town");
        logger.info("######################################## {}", hqName);

        HeadOffice headOffice = new HeadOffice(hqName, hqNumber, contact_ps, contact, mobile_no, email, location, session("Username"), currentDate.format(formatter), Boolean.TRUE);
        headOffice.setIsDeleted(Boolean.FALSE);
        headOffice.save();

        logger.info("Successfully Saved Head[JSGRID] OfficeID++++{}++++ Head OfficeName +++++{}", hqNumber, hqName);

        return ok();
    }

    public Result postEditHeadOffice() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String Id = requestData.get("id");
        String headOfficeName = requestData.get("Full_Names");
        String officeNumber = requestData.get("officeNumber");
        String emailAddress = requestData.get("emailAddress");
        String town = requestData.get("town");
        String mobileNumber = requestData.get("mobileNumber");
        String contactPerson = requestData.get("contactPerson");
        String contactPersonId = requestData.get("contactPersonId");


        HeadOffice oldHeadOffices = HeadOffice.finder.byId(Integer.parseInt(Id));
        if (oldHeadOffices == null) {
            logger.info("The Requested Head Office is Null Head OfficeID++++{}++++ Head OfficeName +++++{}", Id, headOfficeName);
            return ok();
        }
        logger.info("Successfully Edited Head OfficeID++++{}++++ Head OfficeName +++++{}", Id, headOfficeName);
        oldHeadOffices.setIsDeleted(Boolean.FALSE);
        oldHeadOffices.setFull_Names(headOfficeName);
        oldHeadOffices.setCompany(officeNumber);
        oldHeadOffices.setEmailAddress(emailAddress);
        oldHeadOffices.setPosition(town);
        oldHeadOffices.setPhone_1(mobileNumber);
        oldHeadOffices.setEmail_1(contactPerson);
        oldHeadOffices.setContactPersonId(contactPersonId);
        oldHeadOffices.setIsDeleted(Boolean.FALSE);
        oldHeadOffices.save();
        ObjectNode result = Json.newObject();
        result.put("responseCode", "001");
        result.put("responseMsg", "Saved Successfully.");

        return ok();
    }

    public Result postDelete() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String Id = requestData.get("id");
        String headOfficeName = requestData.get("Full_Names");


        HeadOffice oldHeadOffices = HeadOffice.finder.byId(Integer.parseInt(Id));
        if (oldHeadOffices == null) {
            logger.info("The Requested Head Office is Null Head OfficeID++++{}++++ Head OfficeName +++++{}", Id, headOfficeName);
            return ok();
        }
        oldHeadOffices.setIsDeleted(Boolean.TRUE);
        oldHeadOffices.setDeleteApprovedDate(currentDate.format(formatter));
        oldHeadOffices.setDeletedBy(session("Username"));
        oldHeadOffices.save();
        logger.info("The Requested Head Office has been successfully Deleted OfficeID++++{}++++ Head OfficeName +++++{}", Id, headOfficeName);

        return ok();
    }

    public CompletionStage<Result> loadHeadOffice() {

        Executor myEc = HttpExecution.fromThread((Executor) esbExecutionContext);

        logger.info("Loading HeadOffices....for user {} and Branch {} ", session().get("Username"), session().get("branch"));

        return QueryHeadOffices().thenApplyAsync(headOffices -> ok(Json.toJson(headOffices)), myEc);
    }

    private CompletionStage<List<HeadOffice>> QueryHeadOffices() {
        List<HeadOffice> offices = HeadOffice.finder.query().where().eq("IsDeleted", Boolean.FALSE).findList();

        logger.info("++++++++++++++++RESPONSE FROM DB {} ", postDetailsSerializer.serialize(offices));
        return CompletableFuture.completedFuture(offices);
    }

public static void mailHealthStatus(String){

    String subject = "RE: HEALTH CHECK " + "\n";
    String body = session().get("Username") + " Searched for Company Name: " + businessName + "on " + currentDate + " and received \t RESPONSE: " + responseMsg;
    SendEmail.sendHealthCheckEmail(subject, body);

}
*/
}
