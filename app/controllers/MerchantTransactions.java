package controllers;

import models.MerchantTransactionsModel;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import play.Logger;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.concurrent.HttpExecution;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.merchant_transactions.merchant_transactions;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

public class MerchantTransactions extends Controller {
    private EsbExecutionContext esbExecutionContext;
    private final Logger.ALogger logger = Logger.of(this.getClass());

    private Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");


    @Inject
    private FormFactory formFactory;

    @Inject
    public MerchantTransactions(EsbExecutionContext esbExecutionContext) {
        this.esbExecutionContext = esbExecutionContext;
    }

    @Security.Authenticated(Secured.class)
    public Result showMerchantTransactions() {
        return ok(merchant_transactions.render());
    }
    public CompletionStage<Result> loadMerchantTransactions() {

        Executor myEc = HttpExecution.fromThread((Executor) esbExecutionContext);

        logger.info("Loading MerchantTransactions######################################### {} and Branch {} ", session().get("Username"), session().get("branch"));

        return QueryMerchantTransactions().thenApplyAsync(transactions -> ok(Json.toJson(transactions)), myEc);
    }

    private CompletionStage<List<MerchantTransactionsModel>> QueryMerchantTransactions() {
        List<MerchantTransactionsModel> transactions = MerchantTransactionsModel.finder.all();

        return CompletableFuture.completedFuture(transactions);
    }
}
