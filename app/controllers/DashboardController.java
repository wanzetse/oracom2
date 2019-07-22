package controllers;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import be.objectify.deadbolt.java.actions.Pattern;
import models.Profiles;
import models.UserManagement;
import modules.email.SendEmail;
import modules.sms.SendSms;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.concurrent.HttpExecution;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.user_crud;
import views.html.user_management;
import views.html.users.create_user;
import views.html.users.delete_user;
import views.html.users.edit_user;
import views.html.users.reset_pass;

import javax.inject.Inject;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import static controllers.LoginController.ProfileName;

@Security.Authenticated(Secured.class)
public class DashboardController extends Controller {

    private EsbExecutionContext esbExecutionContext;
    private final Logger.ALogger logger = Logger.of(this.getClass());
    private final ActorRef smsActor;
    private SendEmail sendEmail;
    private LoginController loginController;
    private ActorSystem actorSystem;
    public static String SMS;
    public static String emailToSend;
    private Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
    @Inject
    FormFactory formFactory;

    @Inject
    public DashboardController(EsbExecutionContext esbExecutionContext, ActorSystem system) {
        this.esbExecutionContext = esbExecutionContext;
        smsActor = system.actorOf(SmsController.getProps());

    }

    public Result createUser() {
        Form<UserManagement> userManagementForm = formFactory.form(UserManagement.class);

        return ok(create_user.render());
    }

    //@SubjectPresent
    //@Pattern("user.create")
    public Result create_user() {

        return ok(create_user.render());
    }

    public Result edit_user() {

        return ok(create_user.render());
    }

    public Result delete_user() {

        return ok(create_user.render());
    }

    public Result resetPass() {

        return ok(create_user.render());
    }

    /*
        public Result SaveUser() {
            DynamicForm requestData = formFactory.form().bindFromRequest();
            String firstName = requestData.get("first_name");
            String mid_name = requestData.get("mid_name");
            String last_name = requestData.get("last_name");
            String mobile_number = requestData.get("mobile_number");
            String email = requestData.get("email");
            String idNumber = requestData.get("idNumber");
            String RoleId = requestData.get("roleId");
            String BranchId = requestData.get("branchId");

            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            String pass = firstName + generateRandom(4);

            logger.info("++++++++++++++++++++++++++User Password {}++++++++++++++++++++++++", pass);


            UserManagement userManagement = new UserManagement(firstName, mid_name, last_name, mobile_number, email, firstName + "." + last_name, BCrypt.hashpw(pass, BCrypt.gensalt()), "0", idNumber);

            userManagement.setCreatedBy(session().get("Username"));
            userManagement.setCreateApproved(Boolean.FALSE);
            userManagement.setDateCreated(currentDate.format(formatter));
            userManagement.setRoleId(Integer.parseInt(RoleId));


            userManagement.save();

            return ok(user_management.render());
        }
        */
    public Result SaveUser() {

        Form<UserManagement> userManagementForm = formFactory.form(UserManagement.class).bindFromRequest();
        sendEmail = new SendEmail();

        UserManagement userManagement = userManagementForm.get();

        LocalDate currentDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        UserManagement oldUser = UserManagement.finder.query().where().eq("user_name", userManagement.email).findOne();

        String formattedPhoneNumber = userManagement.mobile_number.replaceFirst("0", "254");

        String pass = userManagement.getFirst_name() + generateRandom(6);
        logger.info("#############################Saved Password****************{}====" + pass);


        if (oldUser != null && oldUser.email.equals(userManagement.email)) {

            SMS = "Dear\t " +
                    userManagement.first_name + ", You have successfully reset a user account as an\t " +
                    userManagement.RoleName + ". Your username is " + userManagement.email + " Your password is: " + pass;

            emailToSend = "Dear\t " + userManagement.first_name + ", You have successfully reset a user account as an " + userManagement.getProfileName() + "\n. Your username is " + userManagement.email + "\t" +
                    ". Your password is: " + pass + ".";


        } else {

//sms
            SMS = "Dear\t" +
                    userManagement.first_name + ", You have successfully created a user account as an\t" +
                    userManagement.RoleName + " .Your username is " + userManagement.email + " PASSWORD is: " + pass;

            //email
            emailToSend = "Dear\t " + userManagement.first_name + ", You have successfully created a user account as an " + userManagement.getProfileId() + "\n. Your username is " + userManagement.email + "\t" +
                    ". Your password is: " + pass + ".";


        }

        sendEmail.sendPasswordEmail(emailToSend, userManagement.email);
        SendSms.sendAccountCreationSMS(SMS, formattedPhoneNumber);

        logger.info("#############################ROLE NAME****************{}====" + SMS);
        logger.info("#############################SMS sent to SYSTEM USER****************{}====" + SMS);

        // smsActor.tell(new SmsController.smsObject(formattedPhoneNumber, SMS), ActorRef.noSender());

        logger.info("User Request Branch Name....{}... and ProfileName ....{} ", userManagement.getBranchId(), userManagement.getProfileName());

        userManagement.setCreatedBy(session().get("Username"));
        userManagement.setCreateApproved(Boolean.FALSE);
        userManagement.setUser_name(userManagement.email);
        // user.setProfileName(user.RoleName);
        userManagement.setDateCreated(currentDate.format(formatter));
        userManagement.setPassword(BCrypt.hashpw(pass, BCrypt.gensalt()));


        userManagement.save();

        return ok(create_user.render());
    }


    public Result loadCrudPage() {
        Form<UserManagement> userManagementForm = formFactory.form(UserManagement.class);

        return ok(user_crud.render());
    }

    public CompletionStage<Result> loadUsers() {

        Executor myEc = HttpExecution.fromThread((Executor) esbExecutionContext);

        logger.info("Loading branches....for user {} and Branch {} ", session().get("Username"), session().get("branch"));

        return QueryUsers().thenApplyAsync(users -> ok(Json.toJson(users)), myEc);
    }

    private CompletionStage<List<UserManagement>> QueryUsers() {
        List<UserManagement> userManagements = UserManagement.finder.query().where().eq("Deleted", Boolean.FALSE).findList();

        return CompletableFuture.completedFuture(userManagements);
    }

    public static String generateRandom(int length) {
        String characters = "0123456789";
        if (length <= 0) {
            throw new IllegalArgumentException("String length must be a positive integer");
        }

        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }
}
