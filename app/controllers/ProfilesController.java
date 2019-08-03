package controllers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import be.objectify.deadbolt.java.actions.*;
import models.Branch;
import models.Profiles;
import models.UserRoles;
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
import views.html.profiles.main_profile;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

@Security.Authenticated(Secured.class)
@DeferredDeadbolt
//@Pattern("user.create")
public class ProfilesController extends Controller {
    private EsbExecutionContext esbExecutionContext;
    private final Logger.ALogger logger = Logger.of(this.getClass());
    private static int len;

    private Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
    @Inject
    FormFactory formFactory;

    @Inject
    public ProfilesController(EsbExecutionContext esbExecutionContext) {
        this.esbExecutionContext = esbExecutionContext;
    }

    public Result showProfiles() {

        return ok(main_profile.render());
    }

    public CompletionStage<Result> loadProfiles() {
/*

/oracom/ProfilesMaster?id=&RoleName=&dateCreated=&approved=&pageIndex=1&pageSize=10

*/
         DynamicForm rq = formFactory.form().bindFromRequest();
        //json object to send back parameters
        ObjectNode node=Json.newObject();
        //array of string to hold received parameters
        String[] params=new String[6];
        params[0]=rq.get("id");
        params[1]=rq.get("RoleName");
        params[2]=rq.get("dateCreated");
        params[3]=rq.get("approved");
        params[4]=rq.get("pageIndex");
        params[5]=rq.get("pageSize");

        node.put("data",QueryProfiles(params));
        node.put("len",len);

       // Executor myEc = HttpExecution.fromThread((Executor) esbExecutionContext);

        logger.info("Loading branches....for user {} and Branch {} ", session().get("Username"), session().get("branch"));

       return CompletableFuture.completedFuture(ok(node));
    }


    public Result saveProfile() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        Form<UserRoles> rolesForm = formFactory.form(UserRoles.class).bindFromRequest();

        String Id = requestData.get("id");
        String remarks = requestData.get("remarks");
        String profileName = requestData.get("RoleName");
        String profileId = requestData.get("id");
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

       // UserRoles userRoles = new UserRoles(profileName, remarks);
        UserRoles userRoles = rolesForm.get();
      //  userRoles.setRemarks(remarks);
        //userRoles.setRoleName(profileName);
        userRoles.setCreatedBy(session().get("Username"));
        userRoles.setApproved(false);
        userRoles.setDateCreated(currentDate.format(formatter));
        userRoles.save();

        return ok();
    }

    // @SubjectPresent
  //  @Restrict(@Group("admin"))

    public Result editProfile() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String Id = requestData.get("id");
        String remarks = requestData.get("remarks");
        String profileName = requestData.get("RoleName");
        String approved = requestData.get("approved");

        logger.info("####################################################APPROVED STATUS {} ", approved);
        logger.info("####################################################PROFILE NAME {} ", profileName);

        UserRoles oldProfile = UserRoles.finder.byId(Integer.parseInt(Id));
        if (oldProfile == null) {
            return notFound("Not Found");
        }

        oldProfile.setRoleName(profileName);
        oldProfile.setRemarks(remarks);
        oldProfile.setCreatedBy(session().get("Username"));
        oldProfile.setApproved(Boolean.parseBoolean(approved));
        oldProfile.save();
        return ok();
    }

 private  JsonNode QueryProfiles(String[] otherParams) {
/*

/oracom/ProfilesMaster?id=&RoleName=&dateCreated=&approved=&pageIndex=1&pageSize=10

*/

String id=otherParams[0];
String RoleName=otherParams[1];
String dateCreated=otherParams[2];
String approved=otherParams[3];
int pageIndex=Integer.parseInt(otherParams[4]);
int pageSize=Integer.parseInt(otherParams[5]);

        len =UserRoles.finder.query().where()
        .ilike("id", "%"+id+"%")
        .ilike("RoleName", "%"+RoleName+"%")
        .ilike("DateCreated", "%"+dateCreated+"%")
        .ilike("Approved", "%"+approved+"%")
        .findCount();

        List<UserRoles> profiles = UserRoles.finder.query().where()
        .ilike("id", "%"+id+"%")
        .ilike("RoleName", "%"+RoleName+"%")
        .ilike("DateCreated", "%"+dateCreated+"%")
        .ilike("Approved", "%"+approved+"%")
        .setFirstRow(pageIndex)
        .setMaxRows(pageSize)
        .findPagedList()
        .getList();
  return Json.toJson(profiles);

    }

    public Result deleteProfile() {
        DynamicForm requestData = formFactory.form().bindFromRequest();

        String Id = requestData.get("id");

        UserRoles oldProfile = UserRoles.finder.byId(Integer.parseInt(Id));
        if (oldProfile == null) {
            logger.info("The Requested User is Null+=== User++++{}++++ Creator +++++{}", requestData.get("id"), session("Username"));
            return notFound();
        }
        oldProfile.delete();

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

}
