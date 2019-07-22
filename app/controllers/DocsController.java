package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.documents.DocConfiguration;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import play.Logger;
import play.libs.Json;
import play.libs.concurrent.HttpExecution;
import play.mvc.*;
import views.html.docs.docs;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

public class DocsController extends Controller {

    private EsbExecutionContext esbExecutionContext;
    private final Logger.ALogger logger = Logger.of(this.getClass());

    private LocalDate currentDate = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");

    @Inject
    public DocsController(EsbExecutionContext esbExecutionContext) {
        this.esbExecutionContext = esbExecutionContext;
    }

  //  @Security.Authenticated(Secured.class)
    public Result viewDocs() {

        return ok(docs.render());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result postCreateDocConfiguration() {
        JsonNode json = request().body().asJson();
        logger.info("######################################## {}", json);

        boolean ApplnForm = json.get("app_form").asText().equals("1");
        String BizType = json.get("BizType").asText();
        boolean DirectorID = json.get("dir_id").asText().equals("1");
        boolean KraPin = json.get("kra").asText().equals("1");
        boolean CouncilPermit = json.get("c_council").asText().equals("1");
        boolean BusinessRegCert = json.get("biz_reg").asText().equals("1");
        boolean UtilityBill = json.get("U_bill").asText().equals("1");
        boolean BusinessPhoto = json.get("biz_photo").asText().equals("1");
        boolean BankLetter = json.get("bank_l").asText().equals("1");
        boolean CancelledCheque = json.get("c_cheq").asText().equals("1");
        boolean BoardResolution = json.get("b_resolution").asText().equals("1");
        boolean CR12 = json.get("cr12").asText().equals("1");
        boolean RegulatoryAuthorityLetter = json.get("ra_letter").asText().equals("1");
        boolean LastAuditedAccounts = json.get("accounts").asText().equals("1");

        DocConfiguration docs=DocConfiguration.finder.query().where().eq("business_type",BizType).findOne();

        if (docs==null){
            DocConfiguration docConfiguration=new DocConfiguration();
            docConfiguration.setBusinessType(BizType);
            docConfiguration.setApplnForm(ApplnForm);
            docConfiguration.setDirectorID(DirectorID);
            docConfiguration.setKraPin(KraPin);
            docConfiguration.setBusinessRegCert(BusinessRegCert);
            docConfiguration.setUtilityBill(UtilityBill);
            docConfiguration.setBusinessPhoto(BusinessPhoto);
            docConfiguration.setCouncilPermit(CouncilPermit);
            docConfiguration.setBankLetter(BankLetter);
            docConfiguration.setCancelledCheque(CancelledCheque);
            docConfiguration.setBoardResolution(BoardResolution);
            docConfiguration.setCR12(CR12);
            docConfiguration.setRegulatoryAuthorityLetter(RegulatoryAuthorityLetter);
            docConfiguration.setLastAuditedAccounts(LastAuditedAccounts);
            docConfiguration.setCreatedBy(session("Username"));
            docConfiguration.setCreateDate(currentDate.format(formatter));
            docConfiguration.save();

        }else {
            logger.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%% ENTER STEP 2=== {}",ApplnForm);
            docs.setApplnForm(ApplnForm);
            docs.setDirectorID(DirectorID);
            docs.setKraPin(KraPin);
            docs.setBusinessRegCert(BusinessRegCert);
            docs.setUtilityBill(UtilityBill);
            docs.setBusinessPhoto(BusinessPhoto);
            docs.setCouncilPermit(CouncilPermit);
            docs.setBankLetter(BankLetter);
            docs.setCancelledCheque(CancelledCheque);
            docs.setBoardResolution(BoardResolution);
            docs.setCR12(CR12);
            docs.setRegulatoryAuthorityLetter(RegulatoryAuthorityLetter);
            docs.setLastAuditedAccounts(LastAuditedAccounts);
            docs.setCreatedBy(session("Username"));
            docs.setCreateDate(currentDate.format(formatter));
            docs.update();

        }

        ObjectNode result = Json.newObject();
        result.put("responseMsg", "Saved Successfully");
        result.put("ResponseCode", "000");
        return ok(result);
    }


    @Security.Authenticated(Secured.class)
    public CompletionStage<Result> loadDocConfigurations() {
        JsonNode json = request().body().asJson();
        logger.info("######################################## {}", json);

        Executor myEc = HttpExecution.fromThread((Executor) esbExecutionContext);

        return pullDocConfigurations(json.get("biz_type").asText()).thenApplyAsync(Results::ok, myEc);
    }

    private CompletionStage<ObjectNode> pullDocConfigurations(String bizType) {
       DocConfiguration docConfigurations = DocConfiguration.finder.query().where().eq("business_type",bizType).findOne();
       if(docConfigurations!=null) {
           ObjectNode response = Json.newObject();
           response.put("ResponseCode", "000");
           response.put("app_form", docConfigurations.isApplnForm());
           response.put("dir_id", docConfigurations.isDirectorID());
           response.put("c_council", docConfigurations.isCouncilPermit());
           response.put("kra", docConfigurations.isKraPin());
           response.put("biz_reg", docConfigurations.isBusinessRegCert());
           response.put("U_bill", docConfigurations.isUtilityBill());
           response.put("biz_photo", docConfigurations.isBusinessPhoto());
           response.put("bank_l", docConfigurations.isBankLetter());
           response.put("c_cheq", docConfigurations.isCancelledCheque());
           response.put("b_resolution", docConfigurations.isBoardResolution());
           response.put("cr12", docConfigurations.isCR12());
           response.put("ra_letter", docConfigurations.isRegulatoryAuthorityLetter());
           response.put("accounts", docConfigurations.isLastAuditedAccounts());

           logger.info("JSON RESPONSE: {}",response);

           return CompletableFuture.completedFuture(response);
       }else{
           ObjectNode response = Json.newObject();
           response.put("ResponseCode", "001");
           return CompletableFuture.completedFuture(response);

       }
    }
}
