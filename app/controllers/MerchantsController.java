package controllers;

import models.Merchants;
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
import views.html.merchants.main_merchants;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import static controllers.DashboardController.generateRandom;

public class MerchantsController extends Controller{
    private EsbExecutionContext esbExecutionContext;
    private final Logger.ALogger logger = Logger.of(this.getClass());

    private Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");


    @Inject
    private FormFactory formFactory;

    @Inject
    public MerchantsController(EsbExecutionContext esbExecutionContext) {
        this.esbExecutionContext = esbExecutionContext;
    }

    @Security.Authenticated(Secured.class)
    public Result showMerchants() {
        return ok(main_merchants.render());
    }
    public CompletionStage<Result> loadMerchants() {

        Executor myEc = HttpExecution.fromThread((Executor) esbExecutionContext);

        logger.info("Loading Merchants....for user {} and Branch {} ", session().get("Username"), session().get("branch"));

        return QueryMerchants().thenApplyAsync(merchants -> ok(Json.toJson(merchants)), myEc);
    }
    public Result CreateMerchant(){
        DynamicForm requestData = formFactory.form().bindFromRequest();

        String first_name = requestData.get("firstName");
        String mid_name = requestData.get("midName");
        String last_name = requestData.get("lastName");
        String mobile_number = requestData.get("phoneNumber");
        String email = requestData.get("email_Address");
        String payBillNumber = requestData.get("payBillNumber");
        String settlementAc = requestData.get("settlementAc");

        Form<Merchants> merchantsForm=formFactory.form(Merchants.class).bindFromRequest();

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Merchants merchants=merchantsForm.get();
        String pass=first_name+generateRandom(4);
        logger.info("#############################Saved Password****************{}===="+pass);

        merchants.setFirstName(first_name);
        merchants.setMidName(mid_name);
        merchants.setLastName(last_name);
        merchants.setPhoneNumber(mobile_number);
        merchants.setEmail_Address(email);
        merchants.setPayBillNumber(payBillNumber);
        merchants.setSettlementAc(settlementAc);
        merchants.setPassword(BCrypt.hashpw(pass, BCrypt.gensalt()));
        merchants.setApproved(false);
        merchants.setUsername(first_name+"."+mid_name);
        merchants.setCreateDate(currentDate.format(formatter));
        merchants.save();

        return ok();
    }
    public Result editMerchant() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String Id = requestData.get("id");
        String first_name = requestData.get("firstName");
        String mid_name = requestData.get("midName");
        String last_name = requestData.get("lastName");
        String mobile_number = requestData.get("phoneNumber");
        String email = requestData.get("email_Address");
        String payBillNumber = requestData.get("payBillNumber");
        String settlementAc = requestData.get("settlementAc");

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        logger.info("########################## UserEdit Request {}|{}|{}|{}|{}|{}|{}", first_name, mid_name, last_name, mobile_number, email, payBillNumber, settlementAc);

        Merchants merchants = Merchants.finder.byId(Integer.parseInt(Id));
        if (merchants == null) {
            return notFound("Not Found");
        }
        String pass=first_name+generateRandom(4);
        String passCreated = BCrypt.hashpw(pass, BCrypt.gensalt());
        logger.info("########################## PASSWORD {}", pass);

        merchants.setFirstName(first_name);
        merchants.setMidName(mid_name);
        merchants.setLastName(last_name);
        merchants.setPhoneNumber(mobile_number);
        merchants.setEmail_Address(email);
        merchants.setPayBillNumber(payBillNumber);
        merchants.setSettlementAc(settlementAc);
        merchants.setPassword(passCreated);
        merchants.save();
        return ok();
    }

    private CompletionStage<List<Merchants>> QueryMerchants() {
        List<Merchants> merchants = Merchants.finder.all();

        return CompletableFuture.completedFuture(merchants);
    }
}
