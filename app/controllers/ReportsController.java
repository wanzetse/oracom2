package controllers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.ebean.Ebean;
import io.ebean.PagedList;
import io.ebean.*;
import play.libs.Json;
import play.mvc.*;
import views.html.reports.*;
import io.ebean.Ebean;
import models.*;
import java.util.*;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.inject.Inject;
public class ReportsController extends Controller{
	public static int len;
	private static List<EmailReports> Emailreports;
	private static List<SmsReports> Smsreports;
	 @Inject
    public static FormFactory formFactory;
     @Inject
    public ReportsController(FormFactory formFactory) {
        
        this.formFactory = formFactory;

    }
	public Result smsreports(){
   
		return ok(smsreports.render());
	}


	public Result emailreports(){
		
		return ok(emailreports.render());
	}


public CompletionStage<Result> loadSmsReports(){
/*
/oracom/loadSmsreports?type=&SentBy=&SenderName=&SentTo=&SentDate=&DateReceived=
*/
		ObjectNode node=Json.newObject();
        String[] params=new String[9];
        DynamicForm rq = formFactory.form().bindFromRequest();
        params[0]=rq.get("type");
        params[1]=rq.get("SentBy");
        params[2]=rq.get("SenderName");
        params[3]=rq.get("SentTo");
        params[4]=rq.get("SentDate");
        params[5]=rq.get("DateReceived");
        params[6]=rq.get("pageIndex");
        params[7]=rq.get("pageSize");
        node.put("data", QuerySmsReports(params));
        node.put("len",len);
		return CompletableFuture.completedFuture(ok(node));
	}

	public CompletionStage<Result> loadEmailReports(){
		ObjectNode node=Json.newObject();
        String[] params=new String[9];
        DynamicForm rq = formFactory.form().bindFromRequest();
        params[0]=rq.get("type");
        params[1]=rq.get("SentBy");
        params[2]=rq.get("SenderName");
        params[3]=rq.get("SentTo");
        params[4]=rq.get("SentDate");
        params[5]=rq.get("DateReceived");
        params[6]=rq.get("pageIndex");
        params[7]=rq.get("pageSize");
        params[8]=rq.get("received");
        node.put("data", QueryEmailReports(params));
        node.put("len",len);
		return CompletableFuture.completedFuture(ok(node));
	}
   
 public  JsonNode QuerySmsReports(String[] otherParams) {
/*
/oracom/loadSmsreports?type=&SentBy=&SenderName=&SentTo=&SentDate=&DateReceived=

*/
String type=otherParams[0];
String SentBy=otherParams[1];
String SenderName=otherParams[2];
String SentTo=otherParams[3];
String SentDate=otherParams[4];
String DateReceived=otherParams[5];
int pageIndex=Integer.parseInt(otherParams[6]);
int pageSize=Integer.parseInt(otherParams[7]);
String received=otherParams[8];
len = SmsReports.find.query().where()
        .ilike("type", "%"+type+"%")
        .ilike("SentBy", "%"+SentBy+"%")
        .ilike("SenderName", "%"+SenderName+"%")
        .ilike("SentTo", "%"+SentTo+"%")
        .ilike("SentDate", "%"+SentDate+"%")
        .ilike("DateReceived", "%"+DateReceived+"%")
        //.eq("received",received)
      
        .findCount();

       Smsreports= SmsReports.find.query().where()
        .ilike("type", "%"+type+"%")
        .ilike("SentBy", "%"+SentBy+"%")
        .ilike("SenderName", "%"+SenderName+"%")
        .ilike("SentTo", "%"+SentTo+"%")
        .ilike("SentDate", "%"+SentDate+"%")
        .ilike("DateReceived", "%"+DateReceived+"%")
        //.eq("received",received)
        .setFirstRow(pageIndex)
        .setMaxRows(pageSize)
        .findPagedList()
        .getList();
        
return Json.toJson(Smsreports);

}

     public  JsonNode QueryEmailReports(String[] otherParams) {

String type=otherParams[0];
String SentBy=otherParams[1];
String SenderName=otherParams[2];
String SentTo=otherParams[3];
String SentDate=otherParams[4];
String DateReceived=otherParams[5];
int pageIndex=Integer.parseInt(otherParams[6]);
int pageSize=Integer.parseInt(otherParams[7]);
String received=otherParams[8];
len = EmailReports.find.query().where()
        .ilike("type", "%"+type+"%")
        .ilike("SentBy", "%"+SentBy+"%")
        .ilike("SenderName", "%"+SenderName+"%")
        .ilike("SentTo", "%"+SentTo+"%")
        .ilike("SentDate", "%"+SentDate+"%")
        .ilike("DateReceived", "%"+DateReceived+"%")
       // .eq("received",received)
        .findCount();

       Emailreports= EmailReports.find.query().where()
        .ilike("type", "%"+type+"%")
        .ilike("SentBy", "%"+SentBy+"%")
        .ilike("SenderName", "%"+SenderName+"%")
        .ilike("SentTo", "%"+SentTo+"%")
        .ilike("SentDate", "%"+SentDate+"%")
        .ilike("DateReceived", "%"+DateReceived+"%")
        //.eq("received",received)
        .setFirstRow(pageIndex)
        .setMaxRows(pageSize)
        .findPagedList()
        .getList();
        
return Json.toJson(Emailreports);
     }
}