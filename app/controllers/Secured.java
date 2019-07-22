package controllers;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class Secured extends Security.Authenticator {


    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("Username");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect(routes.LoginController.postLogin());
    }

    public static String getUser(Context ctx) {
        return ctx.session().get("Username");
    }
    public static boolean isLoggedIn(Context ctx) {
        return (getUser(ctx) != null);
    }
}
