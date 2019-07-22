package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import play.Logger;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.concurrent.HttpExecution;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.merchants.directors;
import views.html.merchants.edit_directors;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

public class DirectorsController extends Controller {
    private EsbExecutionContext esbExecutionContext;
    private final Logger.ALogger logger = Logger.of(this.getClass());
    private LocalDate currentDate = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");

    @Inject
    private FormFactory formFactory;

    @Inject
    public DirectorsController(EsbExecutionContext esbExecutionContext) {
        this.esbExecutionContext = esbExecutionContext;
    }
   /* @Security.Authenticated(Secured.class)
    public Result showDirectors() {

      //  List<Business> business=Business.finder.all();
        return ok(directors.render(business));
    }
*/
    public Result editDirectors() {

        return ok(edit_directors.render());
    }

    public Result postSaveDirector() {
        JsonNode json = request().body().asJson();
        logger.info("######################################## {}", json);

        String fName = json.get("fName").asText();
        String mName = json.get("mName").asText();
        String lName = json.get("lName").asText();
        String mNumber = json.get("mNumber").asText();
        String email_add = json.get("email_add").asText();
        String county = json.get("county").asText();
        String location_name = json.get("location_name").asText();
        String cbs_account = json.get("cbs_account").asText();
        String select_biz = json.get("select_biz").asText();
/*
        Directors directors=new Directors(Integer.parseInt(select_biz),fName,mName,lName,mNumber,email_add,county,location_name,cbs_account);
        directors.setIsDeleted(Boolean.FALSE);
        directors.setCreateDate(currentDate.format(formatter));
        directors.setCreatedby(session("Username"));
        directors.setApproved(Boolean.FALSE);
        directors.save();
*/
        ObjectNode result = Json.newObject();
        result.put("responseCode", "001");
        result.put("responseMsg", "Saved Successfully.");

        return ok(result);
    }
    public Result savejsGridDirector() {
        DynamicForm requestData = formFactory.form().bindFromRequest();

        String firstName = requestData.get("firstName");
        String middleName = requestData.get("middleName");
        String lastName = requestData.get("lastName");
        String mobileNumber = requestData.get("mobileNumber");
        String emailAddress = requestData.get("emailAddress");
        String businessName = requestData.get("businessName");
        String headOfficeName = requestData.get("Full_Names");
/*
        Directors directors=new Directors(Integer.parseInt(businessName),firstName,middleName,lastName,mobileNumber,emailAddress,headOfficeName,headOfficeName,headOfficeName);
        directors.setIsDeleted(Boolean.FALSE);
        directors.setCreateDate(currentDate.format(formatter));
        directors.setCreatedby(session("Username"));
        directors.setApproved(Boolean.FALSE);
        directors.save();
*/

        return ok();
    }
    public Result postEditDirectors(){
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String Id = requestData.get("Id");
/*

        Directors oldDirectors = Directors.finder.byId(Integer.parseInt(Id));
        if (oldDirectors == null) {
            return notFound("Not Found");
        }
        */
        return ok();
    }
    /*
    public CompletionStage<Result> loadDirectors() {

        Executor myEc = HttpExecution.fromThread((Executor) esbExecutionContext);

        logger.info("Loading Directors....for user {} and Branch {} ", session().get("Username"), session().get("branch"));

        return QueryDirectors().thenApplyAsync(directors -> ok(Json.toJson(directors)), myEc);
    }

    private CompletionStage<List<Directors>> QueryDirectors() {
        List<Directors> directors = Directors.finder.all();

        return CompletableFuture.completedFuture(directors);
    }
    */
}
