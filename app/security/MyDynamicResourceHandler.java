package security;

import be.objectify.deadbolt.java.ConfigKeys;
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import play.mvc.Http;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

public class MyDynamicResourceHandler implements DynamicResourceHandler {


    @Override
    public CompletionStage<Boolean> isAllowed(final String name,
                                              final Optional<String> meta,
                                              final DeadboltHandler deadboltHandler,
                                              final Http.Context ctx) {
        return deadboltHandler.getSubject(ctx)
                .thenApply(option -> option.isPresent() && option.get().getIdentifier()
                        .contains(name));
    }

    @Override
    public CompletionStage<Boolean> checkPermission(String permissionValue, Optional<String> meta, DeadboltHandler deadboltHandler, Http.Context ctx) {
        return deadboltHandler.getSubject(ctx)
                .thenApplyAsync(option ->
                        option.map(subject -> subject.getPermissions()
                                .stream().anyMatch(perm -> perm.getValue().contains(permissionValue)))
                                .orElseGet(() -> (Boolean) ctx.args.getOrDefault(ConfigKeys.PATTERN_INVERT,
                                        false)));
    }
}
