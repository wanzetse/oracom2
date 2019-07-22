package controllers;

import be.objectify.deadbolt.java.actions.Pattern;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import play.Logger;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.concurrent.HttpExecution;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.permissions.editpermissions;
import views.html.permissions.permissions;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

public class PermissionsController extends Controller {
    private EsbExecutionContext esbExecutionContext;
    private final Logger.ALogger logger = Logger.of(this.getClass());

    private Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");


    @Inject
    private FormFactory formFactory;

    @Inject
    public PermissionsController(EsbExecutionContext esbExecutionContext) {
        this.esbExecutionContext = esbExecutionContext;
    }

    @Security.Authenticated(Secured.class)
    public CompletionStage<Result> editPermissions() {

        Executor myEc = HttpExecution.fromThread((Executor) esbExecutionContext);

        return QueryPermissions().thenApplyAsync(permit -> {
            if (permit == null) {
                flash("response", "fail");
                return ok(permissions.render(permit));
            } else {
                flash("response", "successful");
                return ok(editpermissions.render(permit));
            }

        }, myEc);
    }

    @Security.Authenticated(Secured.class)
    public CompletionStage<Result> loadPermissions() {

        Executor myEc = HttpExecution.fromThread((Executor) esbExecutionContext);

        return QueryPermissions().thenApplyAsync(perm -> {
            if (perm == null) {
                flash("response", "fail");
                return ok(permissions.render(perm));
            } else {
                flash("response", "success");
                return ok(permissions.render(perm));
            }

        }, myEc);
    }

    private CompletionStage<List<UserRoles>> QueryPermissions() {
        List<UserRoles> userRoles = UserRoles.finder.all();

        return CompletableFuture.completedFuture(userRoles);
    }

    @BodyParser.Of(BodyParser.Json.class)
   // @Pattern("profile.edit")
    public Result postEditPermission() {
        JsonNode json = request().body().asJson();
        logger.info("######################################## {}", json);

        tb_UserAuthorization userAuth = tb_UserAuthorization.finder.query().where().eq("role", json.get("roles").asText()).findOne();

        if (userAuth != null) {
            userAuth.setPermissions(json.get("permissions").asText());
            userAuth.save();
            ObjectNode result = Json.newObject();
            result.put("response", "Saved Successfully");
            return ok(result);
        } else {
            ObjectNode result = Json.newObject();
            result.put("response", "Record does not exist");
            return ok(result);
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result postCreatePermissions() {
        JsonNode json = request().body().asJson();
        logger.info("######################################## {}", json);

        tb_UserAuthorization userAuth = tb_UserAuthorization.finder.query().where().eq("role", json.get("roles").asText()).findOne();

        if (userAuth == null) {
            tb_UserAuthorization userAuthorization = new tb_UserAuthorization(json.get("roles").asText(), json.get("permissions").asText());
            userAuthorization.save();

            tbUserRole tbUserRole = new tbUserRole(json.get("roles").asText());
            tbUserRole.save();

            String[] permissionsArray = json.get("permissions").asText().split(",");

            for (String name : permissionsArray) {
                tbUserPermissions tbUserPermissions = new tbUserPermissions(name);
                tbUserPermissions.save();

                logger.info("++++++++++++++++++++++++++ {}", name);
                /*
                AuthorisedUser authorisedUser=new AuthorisedUser(session().get("Username"), Collections.singletonList(tbUserRole), Collections.singletonList(tbUserPermissions));
                authorisedUser.save();
                */
            }


            ObjectNode result = Json.newObject();
            result.put("response", "Saved Successfully");
            return ok(result);

        } else {
            ObjectNode result = Json.newObject();
            result.put("response", "Record Exists");
            return ok(result);
        }
    }

    @Security.Authenticated(Secured.class)
    public CompletionStage<Result> loadEditPermissions() {
        JsonNode json = request().body().asJson();
        logger.info("######################################## {}", json);

        Executor myEc = HttpExecution.fromThread((Executor) esbExecutionContext);

        return pullUserPermissions().thenApplyAsync(permit -> {
            if (permit == null) {

                return ok(permissions.render(permit));
            } else {
                tb_UserAuthorization userAuthorization = tb_UserAuthorization.finder.query().where().eq("role", json.get("role").asText()).select("permissions").findOne();

                logger.info("######################################## {}", Json.toJson(userAuthorization));

                return ok(Json.toJson(userAuthorization));
            }

        }, myEc);
    }

    private CompletionStage<List<UserRoles>> pullUserPermissions() {
        List<UserRoles> userRoles = UserRoles.finder.all();
        return CompletableFuture.completedFuture(userRoles);
    }
}
