package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.ebean.Ebean;
import models.Branch;
import models.Message;
import models.PersonsByRegion;
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
import play.mvc.*;
import views.html.persons.persons_by_region;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import static modules.ExcelDataConfig.importExcelPersonsByRegion;

public class PersonsByRegionController extends Controller {

    private EsbExecutionContext esbExecutionContext;
    private final Logger.ALogger logger = Logger.of(this.getClass());
    private LocalDate currentDate = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");

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
    @Inject
    private FormFactory formFactory;


    @Inject
    public PersonsByRegionController(EsbExecutionContext esbExecutionContext, FormFactory formFactory) {
        this.esbExecutionContext = esbExecutionContext;
        this.formFactory = formFactory;
    }


    // @Security.Authenticated(Secured.class)
    public CompletionStage<Result> showPersonsByRegion() {

        Form<FormDataController> personsByRegion = formFactory.form(FormDataController.class);
        Form<Message> personEmailForm = formFactory.form(Message.class);

        return CompletableFuture.completedFuture(ok(persons_by_region.render(personsByRegion, personEmailForm)));
    }


    @BodyParser.Of(MyMultipartFormDataBodyParserController.class)
    //@SubjectPresent
    //@Pattern("branch.create")
    public CompletionStage<Result> uploadPersonsByRegion() {

        final Http.MultipartFormData<File> formData = request().body().asMultipartFormData();
        final Http.MultipartFormData.FilePart<File> filePart = formData.getFile("name");
        final File file = filePart.getFile();

        String createdBy = session().get("Username");
        String dateCreated = HeadOfficeController.currentDateAndTime;
        try {

            importExcelPersonsByRegion(file, createdBy, dateCreated);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return CompletableFuture.completedFuture(redirect(routes.PersonsByRegionController.showPersonsByRegion()));
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

    public CompletionStage<Result> postDeleteSelected() {
        //DynamicForm requestData = formFactory.form().bindFromRequest();
        // String Id = requestData.get("Id");

        List<PersonsByRegion> selectedPersons = PersonsByRegion.finder.query().where().eq("personSelected", Boolean.TRUE).findList();

        Ebean.beginTransaction();
        Ebean.deleteAll(selectedPersons);
        Ebean.commitTransaction();

        if (selectedPersons == null) {
            //logger.info("The Requested User is Null+=== User++++{}++++ Creator +++++{}", requestData.get("id"), session("Username"));
            return CompletableFuture.completedFuture(notFound());
        }
        Ebean.deleteAll(selectedPersons);
        // logger.info("The Requested User has been successfully Deleted UserNumber++++{}++++ DeletedBy +++++{}", requestData.get("mobile_number"), session("Username"));

        return CompletableFuture.completedFuture(redirect(routes.PersonsByRegionController.showPersonsByRegion()));
    }

    public CompletionStage<Result> postDeleteAll() {
        try {

            List<PersonsByRegion> persons = PersonsByRegion.finder.all();
            Ebean.beginTransaction();
            Ebean.deleteAll(persons);
            Ebean.commitTransaction();

            if (persons == null) {
                ////  logger.info("The Requested User is Null+=== User++++{}++++ Creator +++++{}", requestData.get("id"), session("Username"));
                return CompletableFuture.completedFuture(notFound());
            }
            // logger.info("The Requested User has been successfully Deleted UserNumber++++{}++++ DeletedBy +++++{}", requestData.get("mobile_number"), session("Username"));

        } catch (Exception e) {
            // Ebean.endTransaction();
            e.printStackTrace();

        }

        return CompletableFuture.completedFuture(redirect(routes.PersonsByRegionController.showPersonsByRegion()));
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


    public Result editPersonByRegion() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String Id = requestData.get("Id");
        String phone_number = requestData.get("person_phone");
        String surname = requestData.get("person_Surname");
        String other_names = requestData.get("person_Othernames");
        String CountyName = requestData.get("person_CountyName");
        String Constituency_name = requestData.get("person_Constituency_name");
        String WardName = requestData.get("person_WardName");
        String PollingName = requestData.get("person_PollingName");
        String Email = requestData.get("person_Email_address");
        String Gender = requestData.get("person_Gender");
        String Comments = requestData.get("person_comment");
        String selected = requestData.get("personSelected");


        logger.info("####################################################APPROVED STATUS {} ", phone_number);

        PersonsByRegion person = PersonsByRegion.finder.byId(Integer.parseInt(Id));
        if (person == null) {
            return notFound("Not Found");
        }

        // logger.info("####################################################Old Profile{} ", headOffice.email);

        person.setPerson_phone(phone_number);
        person.setPerson_Surname(surname);
        person.setPerson_Othernames(other_names);
        person.setPerson_CountyName(CountyName);
        person.setPerson_Constituency_name(Constituency_name);
        person.setPerson_WardName(WardName);
        person.setPerson_PollingName(PollingName);
        person.setPerson_Email_address(Email);
        person.setPerson_Gender(Gender);
        person.setPerson_comment(Comments);
        person.setPersonSelected(Boolean.parseBoolean(selected));
        person.setPerson_CreateDate(HeadOfficeController.currentDateAndTime);
        person.setPerson_CreatedBy(session().get("Username"));
        person.setPersonSelected(Boolean.parseBoolean(selected));


        person.save();

        return ok();
    }


    public Result saveJsGridPersonsByRegion() {
        Form<PersonsByRegion> personsByRegionForm = formFactory.form(PersonsByRegion.class).bindFromRequest();

        PersonsByRegion personsByRegion = personsByRegionForm.get();
        personsByRegion.setPerson_CreatedBy(session().get("Username"));
        personsByRegion.setPersonSelected(Boolean.FALSE);
        personsByRegion.setPerson_CreateDate(HeadOfficeController.currentDateAndTime);

        personsByRegion.save();

        return ok();
    }


    public Result postEditPersonsByRegion() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String Id = requestData.get("Id");
        String approved = requestData.get("personSelected");

        logger.info("####################################################APPROVED STATUS {} ", approved);

        PersonsByRegion oldpersonsByRegion = PersonsByRegion.finder.byId(Integer.parseInt(Id));

        if (oldpersonsByRegion == null) {
            return notFound("Not Found");
        }
        return ok();

    }


    public CompletionStage<Result> loadPersonsByRegions() {

        Executor myEc = HttpExecution.fromThread((Executor) esbExecutionContext);

        logger.info("Loading businesses....for user {} and Branch {} ", session().get("Username"), session().get("branch"));

        return QueryPersonsByRegions().thenApplyAsync(personsByRegions -> ok(Json.toJson(personsByRegions)), myEc);
    }


    private CompletionStage<List<PersonsByRegion>> QueryPersonsByRegions() {
        List<PersonsByRegion> personsByRegions = PersonsByRegion.finder.all();

        return CompletableFuture.completedFuture(personsByRegions);
    }

}
