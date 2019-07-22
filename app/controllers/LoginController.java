package controllers;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import be.objectify.deadbolt.java.actions.Pattern;
import be.objectify.deadbolt.java.actions.SubjectPresent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import modules.email.SendEmail;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.concurrent.HttpExecution;
import play.mvc.*;
import views.html.dashboard;
import views.html.login;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.xml.ws.spi.http.HttpContext;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import static controllers.DashboardController.SMS;
import static play.libs.concurrent.HttpExecution.fromThread;

public class LoginController extends Controller {
    private EsbExecutionContext esbExecutionContext;
    private final Logger.ALogger logger = Logger.of(this.getClass());
    private LocalDate currentDate = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
    private final ActorRef smsActor;
    private SendEmail sendEmail;

    private ObjectNode result;
    @Inject
    private FormFactory formFactory;

    public static String first_name;
    public static String ProfileName;
    public static String mobile_number;
    public static String email;
    public static String roleName;


    @Inject
    public LoginController(EsbExecutionContext esbExecutionContext, ActorSystem system) {
        this.esbExecutionContext = esbExecutionContext;
        smsActor = system.actorOf(SmsController.getProps());

    }

    @Security.Authenticated(Secured.class)
    public Result dashboard() {

        Form<User> loginForm = formFactory.form(User.class);
        return ok(dashboard.render(loginForm));
    }


    public Result login() {
        Form<User> loginForm = formFactory.form(User.class);
        return ok(login.render(Secured.isLoggedIn(ctx()), loginForm));


    }

    public Result logout() {

        Http.Context.current().session().clear();

        return redirect(routes.LoginController.postLogin());
    }

    /*
        public Result EditUser() {
            DynamicForm requestData = formFactory.form().bindFromRequest();
            String Id = requestData.get("id");
            String first_name = requestData.get("first_name");
            String mid_name = requestData.get("mid_name");
            String last_name = requestData.get("last_name");
            String mobile_number = requestData.get("mobile_number");
            String email = requestData.get("email");
            String ProfileName = requestData.get("Role_Name");
            String BranchName = requestData.get("Company_Name");
            String approved = requestData.get("Website");
            String roleId = requestData.get("roleId");
            String BranchId = requestData.get("branchId");

            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            logger.info("########################## UserEdit Request {}|{}|{}|{}|{}|{}|{}", first_name, mid_name, last_name, mobile_number, email, ProfileName, BranchName);

            UserManagement oldUser = UserManagement.finder.byId(Integer.parseInt(Id));
            if (oldUser == null) {
                return notFound("Not Found");
            }
            String rawPassword = oldUser.getFirst_name() + generateTempPass(4);
            String passCreated = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
            logger.info("########################## PASSWORD {}", rawPassword);

            oldUser.setFirst_name(first_name);
            oldUser.setMid_name(mid_name);
            oldUser.setLast_name(last_name);
            oldUser.setMobile_number(mobile_number);
            oldUser.setEmail_1(email);
            oldUser.setPassword(passCreated);
            //  oldUser.setRoleId(Integer.parseInt(roleId));
            oldUser.setCreateApprovedBy(session().get("Username"));

            if (Boolean.parseBoolean(approved)) {
                oldUser.setCreateApprovedDate(currentDate.format(formatter));
                oldUser.setCreateApproved(Boolean.FALSE);
            } else {
                oldUser.setRejected(Boolean.TRUE);
                oldUser.setDateRejected(currentDate.format(formatter));
            }
            oldUser.save();
            return ok();
        }
    */
    @Security.Authenticated(Secured.class)
    // @Pattern("user.create")
    public Result EditUser() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String Id = requestData.get("id");
        first_name = requestData.get("first_name");
        String mid_name = requestData.get("mid_name");
        String last_name = requestData.get("last_name");
        mobile_number = requestData.get("mobile_number");
        email = requestData.get("email");
        ProfileName = requestData.get("RoleName");
        String profileId = requestData.get("ProfileId");
        String BranchName = requestData.get("BranchName");
        String branchId = requestData.get("BranchId");
        String approved = requestData.get("CreateApproved");


        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String formatedPhoneNumber = mobile_number.replaceFirst("0", "+254");

        //smsActor.tell(new SmsController.smsObject(formatedPhoneNumber, SMS), ActorRef.noSender());


        logger.info("########################## UserEdit Request {}|{}|{}|{}|{}|{}|{}", first_name, mid_name, last_name, mobile_number, email, ProfileName, BranchName);
        logger.info("########################## Profile ID Request {}|", ProfileName);

        UserManagement oldUser = UserManagement.finder.byId(Integer.parseInt(Id));
        if (oldUser == null) {
            return notFound("Not Found");
        }
        String rawPassword = oldUser.getFirst_name() + generateTempPass(6);
        String passCreated = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

        logger.info("########################## PASSWORD {}", rawPassword);
        logger.info("########################## PROFILE_ID  |{}|", ProfileName);


        oldUser.setFirst_name(first_name);
        oldUser.setMid_name(mid_name);
        oldUser.setLast_name(last_name);
        oldUser.setMobile_number(mobile_number);
        oldUser.setEmail(email);
        oldUser.setProfileName(ProfileName);
        // oldUser.setBranchName(BranchName);
        //  oldUser.setProfileId(Integer.parseInt(profileId));
        // oldUser.setBranchId(Integer.parseInt(branchId));
        oldUser.setPassword(passCreated);
        oldUser.setCreateApprovedBy(session().get("Username"));

        if (Boolean.parseBoolean(approved)) {
            oldUser.setCreateApprovedDate(currentDate.format(formatter));
            oldUser.setCreateApproved(Boolean.FALSE);
        } else {
            oldUser.setRejected(Boolean.TRUE);
            oldUser.setDateRejected(currentDate.format(formatter));
        }
        oldUser.save();
        return ok();
    }


    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> postLogin() {

        Http.Context.current().session().clear();
        JsonNode json = request().body().asJson();

        logger.info("######################################## LOGIN REQUEST {}", json);

        String username = json.get("username").asText();
        String password = json.get("password").asText();

        Executor myEc = fromThread((Executor) esbExecutionContext);

        logger.info("Login request for user | {} |", username);


        if (username.equals("agile") && password.equals("employee@agile")) {

            session("Username", username);
            ObjectNode result = Json.newObject();
            result.put("login", "success");
            result.put("responseMsg", "success");
            session("branch", "karatu");
            session("RoleName", "root");
            result.put("response", "Login Successful");

            logger.info("++++++++++++++++++++++++++++++++++++++++++++++++ Profile |{}|", session().get("RoleName"));
            logger.info("++++++++++++++++++++++++++++++++++++++++++++++++ LOGIN FAILED for   |{}|", json);
            logger.info("++++++++++++++++++++++++++++++++++++++++++++++++ Response for   |{}|", result.toString());

            return CompletableFuture.completedFuture(ok(result));

        } else {

            ObjectNode failedLogin = Json.newObject();
            failedLogin.put("response","invalid");

        /*
        return AttemptLogin(username).thenApplyAsync(loginResponse -> {



            if (loginResponse == null) {

                session("Username", username);
                ObjectNode result = Json.newObject();
                result.put("login", "fail");
                result.put("response", "success");
                session("branch", "karatu");
                session("RoleName", "user");
                result.put("response", "Invalid Credentials");

                logger.info("++++++++++++++++++++++++++++++++++++++++++++++++ Profile |{}|", session().get("RoleName"));
                logger.info("++++++++++++++++++++++++++++++++++++++++++++++++ LOGIN FAILED for   |{}|", json);

                return ok(result);
            }

            logger.info("%%%%%%%%%%%%%%%%%%%%%%%%%%RAW PASS FROM DB | {} |", loginResponse.getPassword());

            String passFromDB = loginResponse.getPassword().replace("\\", "");

            logger.info("%%%%%%%%%%%%%%%%%%%%%%%%%%ALTERED PASS FROM DB | {} |", passFromDB);

            if (BCrypt.checkpw(password, loginResponse.getPassword())) {
                session("Username", username);
                ObjectNode result = Json.newObject();
                result.put("login", "success");
                result.put("response", "success");
                session("branch", "karatu");
                session("profile", "user");
                session("RoleName", getUserRoleName(username).toString());

                logger.info("++++++++++++++++++++++++++++++++++++++++++++++++ Profile |{}|", session().get("RoleName"));

                return ok(result);
            } else {
                logger.info("Query Response from DB is null for username {} ", username);

                session("Username", username);

                ObjectNode result = Json.newObject();
                result.put("login", "fail");
                result.put("response", "Invalid Credentials");
                return ok(result);

            }


        }, myEc);

*/
            return CompletableFuture.completedFuture(ok(failedLogin));

        }
    }
    public Result postDelete() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String Id = requestData.get("id");


        UserManagement oldUser = UserManagement.finder.byId(Integer.parseInt(Id));
        if (oldUser == null) {
            logger.info("The Requested User is Null+=== User++++{}++++ Creator +++++{}", requestData.get("mobile_number"), session("Username"));
            return ok();
        }
        oldUser.setDeleted(Boolean.TRUE);
        oldUser.setDeletedBy(session("Username"));
        oldUser.setDeleteDate(currentDate.format(formatter));
        oldUser.save();
        logger.info("The Requested User has been successfully Deleted UserNumber++++{}++++ DeletedBy +++++{}", requestData.get("mobile_number"), session("Username"));

        return ok();
    }

    private CompletionStage<UserManagement> AttemptLogin(String username) {
        UserManagement userManagement = UserManagement.finder.query().where().eq("user_name", username).findOne();


        return CompletableFuture.completedFuture(userManagement);
    }

    private CompletionStage<String> getUserRoleName(String username) {

        UserManagement userManagement = UserManagement.finder.query().where().eq("user_name", username).findOne();

      /*  List<UserManagement> userManagements = UserManagement.finder.all();

        String roleName = UserManagement.finder.query()
                .where()
                .eq("user_name", username)
                .select("RoleName")
                .findOne().RoleName;
*/
        if (userManagement != null) {

            roleName = userManagement.getProfileName();
            return CompletableFuture.completedFuture(roleName);

        }

        roleName = "user";
        logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ RoleName |{}|", roleName);
        return CompletableFuture.completedFuture(roleName);

    }

    public Result deleteUser() {
        DynamicForm requestData = formFactory.form().bindFromRequest();

        String Id = requestData.get("id");

        UserManagement userManagement = UserManagement.finder.byId(Integer.parseInt(Id));
        if (userManagement == null) {
            logger.info("The Requested User is Null+=== User++++{}++++ Creator +++++{}", requestData.get("id"), session("Username"));
            return notFound();
        }
        userManagement.delete();

        logger.info("The Requested User has been successfully Deleted UserNumber++++{}++++ DeletedBy +++++{}", requestData.get("mobile_number"), session("Username"));
/*
        String resetAutoIncrement = "ALTER TABLE TBROLE ALTER COLUMN id RESTART WITH 1";

        final Update update = Ebean.createUpdate(UserRoles.class, resetAutoIncrement);
        final int updatedRows = update.execute();
        if (updatedRows > 1) {
            return ok();

        }
        */

        return ok();
    }

    private static String generateTempPass(int length) {
        String characters = "0123456789";
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }
}
