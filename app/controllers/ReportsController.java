package controllers;
import io.ebean.Ebean;
import io.ebean.PagedList;
import io.ebean.*;
import play.libs.Json;
import play.mvc.*;
import views.html.reports.*;
public class ReportsController extends Controller{
	public Result smsreports(){
		return ok(smsreports.render());
	}


	public Result emailreports(){
		return ok(emailreports.render());
	}
}