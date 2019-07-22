package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.HeadOffice;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import play.Logger;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.concurrent.HttpExecution;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.merchants.approve_business;
import views.html.merchants.businesses;
import views.html.merchants.edit_business;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

public class BusinessController extends Controller {
    private EsbExecutionContext esbExecutionContext;
    private final Logger.ALogger logger = Logger.of(this.getClass());
    private LocalDate currentDate = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");


    @Inject
    private FormFactory formFactory;

    @Inject
    public BusinessController(EsbExecutionContext esbExecutionContext) {
        this.esbExecutionContext = esbExecutionContext;
    }


    @Security.Authenticated(Secured.class)
    public Result showBusiness() {

       List<HeadOffice> office=HeadOffice.finder.all();

        return ok(businesses.render(office));
    }

    public Result approveBusiness() {

        return ok(approve_business.render());
    }

    public Result editBusiness() {

        return ok(edit_business.render());
    }

    public Result postSaveBusiness() {
        JsonNode json = request().body().asJson();
        logger.info("######################################## {}", json);

        String organization_name = json.get("organization_name").asText();
        String town = json.get("town").asText();
        String biz_type = json.get("biz_type").asText();
        String company_reg = json.get("company_reg").asText();
        String regType = json.get("regType").asText();
        String kra = json.get("kra").asText();
        String location = json.get("location").asText();
        String HQ = json.get("HQ").asText();
        String no_of_dir = json.get("no_of_dir").asText();
        String MSISDN = json.get("MSISDN").asText();

        /*
        Business business = new Business(organization_name, location, town, biz_type, company_reg, regType, kra, no_of_dir, MSISDN, HQ);
        business.setIsDeleted(Boolean.FALSE);
        business.setCreateDate(currentDate.format(formatter));
        business.setCreatedby(session("Username"));
        business.setHeadOffice(session("Username"));
        business.setApproved(Boolean.FALSE);
        business.setOffice_id(Integer.parseInt(HQ));
        business.save();
*/
        ObjectNode result = Json.newObject();
        result.put("responseCode", "001");
        result.put("responseMsg", "Saved Successfully.");

        return ok(result);
    }

    public Result postEditBusiness() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String Id = requestData.get("Id");
        String branchName = requestData.get("Company_Name");
        String branchCode = requestData.get("Town");
        String email = requestData.get("County");
        String phoneNumber = requestData.get("Company_Branch");
        String commissionAc = requestData.get("Phone_1");
        String location = requestData.get("Cell_2");
        String contactPerson = requestData.get("Cell_3");
        String remarks = requestData.get("Email_2");
        String approved = requestData.get("Website");

        logger.info("####################################################APPROVED STATUS {} ", approved);

        /*Business oldBusiness = Business.finder.byId(Integer.parseInt(Id));
        if (oldBusiness == null) {
            return notFound("Not Found");
        }
        logger.info("####################################################Old Profile{} ", oldBusiness.getBusiness());

        */
        return ok();

    }

    /*
    public CompletionStage<Result> loadBusinesses() {

        Executor myEc = HttpExecution.fromThread((Executor) esbExecutionContext);

        logger.info("Loading businesses....for user {} and Branch {} ", session().get("Username"), session().get("branch"));

        return QueryBusinesses().thenApplyAsync(businesses -> ok(Json.toJson(businesses)), myEc);
    }


    private CompletionStage<List<Business>> QueryBusinesses() {
        List<Business> businesses = Business.finder.all();

        return CompletableFuture.completedFuture(businesses);
    }
    */
}
