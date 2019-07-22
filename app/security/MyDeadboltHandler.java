package security;

import be.objectify.deadbolt.java.AbstractDeadboltHandler;
import be.objectify.deadbolt.java.ConfigKeys;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import be.objectify.deadbolt.java.ExecutionContextProvider;
import be.objectify.deadbolt.java.models.Subject;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

@HandlerQualifiers.MainHandler
public class MyDeadboltHandler extends AbstractDeadboltHandler {
    @Inject
    public MyDeadboltHandler(ExecutionContextProvider executionContextProvider) {
        super(executionContextProvider);

    }

    @Override
    public CompletionStage<Optional<Result>> beforeAuthCheck(Http.Context context) {
        return CompletableFuture.completedFuture(Optional.empty());
    }

    @Override
    public CompletionStage<Result> onAuthFailure(Http.Context context, Optional<String> content) {
        return super.onAuthFailure(context, content);
    }

    @Override
    public CompletionStage<Optional<DynamicResourceHandler>> getDynamicResourceHandler(Http.Context context) {
        return CompletableFuture.supplyAsync(() -> Optional.of(new MyDynamicResourceHandler()));
    }
    /*
    @Override
    public CompletionStage<Optional<? extends Subject>> getSubject(final Http.Context context)
    {
        return CompletableFuture.supplyAsync(() -> Optional.ofNullable(AuthorisedUser.findByUserName(context.session().get("Username"))),
                (Executor) executionContextProvider.get());

    }
    */

//    @Override
//    public CompletionStage<List<? extends Permission>> getPermissionsForRole(final String roleName)
//    {
//        return CompletableFuture.completedFuture(Collections.singletonList(new tbUserPermissions(roleName)));
//    }


    @Override
    public String handlerName()
    {
        return ConfigKeys.DEFAULT_HANDLER_KEY;
    }
}
