package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
/*
import jdk.internal.org.xml.sax.ContentHandler;
import jdk.internal.org.xml.sax.InputSource;
import jdk.internal.org.xml.sax.SAXException;
import jdk.internal.org.xml.sax.XMLReader;
*/
import io.ebean.Ebean;
import io.ebean.PagedList;
import io.ebean.*;
import modules.email.SendEmail;
import modules.sms.SendSms;
//import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import play.libs.Json;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import views.html.branch.main_branch;
import models.*;

import javax.inject.Inject;
import javax.persistence.NonUniqueResultException;
import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import static modules.ExcelDataConfig.readExcel;
import static modules.ExcelDataConfig.sheetColumns;

import static play.libs.concurrent.HttpExecution.fromThread;

@Security.Authenticated(Secured.class)
public class BranchesController extends Controller {

    private EsbExecutionContext esbExecutionContext;
    public static Logger.ALogger logger = Logger.of(BranchesController.class);

    public static int recordCount;

    public static String responseMsg;
    public static String responseCode;
    public String emailFrom;
    public String emailPassword;
    public String emailSubject;
    public String emailBody;
    public String emails;

    public static String SENDER_ID;
    public static String senderIdUsername;
    public static String senderIdPassword;
    public static String SMSbody;

    private SendEmail sendEmail;
    private Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");

    private static List<Branch> branches;

    private static int len;





    @Inject
    public static FormFactory formFactory;

    @Inject
    public BranchesController(EsbExecutionContext esbExecutionContext, FormFactory formFactory) {
        this.esbExecutionContext = esbExecutionContext;
        this.formFactory = formFactory;

    }

    //  @SubjectPresent
    //@Pattern("branch.create")
    public Result showBranches() {
        Form<FormDataController> branches = formFactory.form(FormDataController.class);
        Form<Message> emailForm = formFactory.form(Message.class);

        return ok(main_branch.render(branches, emailForm));
    }




    @BodyParser.Of(MyMultipartFormDataBodyParserController.class)
    //@SubjectPresent
    //@Pattern("branch.create")
    public CompletionStage<Result> upload() {

        final Http.MultipartFormData<File> formData = request().body().asMultipartFormData();
        final Http.MultipartFormData.FilePart<File> filePart = formData.getFile("name");
        final File file = filePart.getFile();

        String createdBy = session().get("Username");
        String dateCreated = HeadOfficeController.currentDateAndTime;
        try {

            readExcel(file, createdBy, dateCreated);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return CompletableFuture.completedFuture(redirect(routes.BranchesController.showBranches()));

    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> sendBulkSMS() {
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();

        SENDER_ID = json.get("senderIDTextField").asText();
        senderIdUsername = json.get("userNameTextField").asText();
        senderIdPassword = json.get("senderIDPasswordTextField").asText();
        SMSbody = json.get("smsbodyTextField").asText();

        if (SMSbody.equals(null) || SENDER_ID.equals(null) || senderIdUsername.equals(null) || senderIdPassword.equals(null)) {

            result.put("result", "empty");

            return CompletableFuture.completedFuture(ok(result));

        } else {
            
            try {
                SendSms.sendSMS(SENDER_ID, senderIdUsername, senderIdPassword, SMSbody);
                result.put("result", "Success!");


            }catch (Exception e){
                e.printStackTrace();
                result.put("result","Authentication Or network Connection failure");
                return CompletableFuture.completedFuture(ok(result));
            }
            String subject = "RE: HEALTH CHECK " + "\n";
            String body = session().get("Username") + " Sent bulk SMS:  on " + HeadOfficeController.dateTimeFormatter.format(HeadOfficeController.currentDateTime) + " and received \t RESPONSE: " + result.get("result");
            SendEmail.sendHealthCheckEmail(subject, body);

            logger.info("+++++++++++++++++++++++++++++++++++++++BULK SMS |{}|", SMSbody);

        }
        return CompletableFuture.completedFuture(ok(result));
    }


    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> sendEmail() {

        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();

        emailFrom = json.get("fromTextField").asText();
        emailPassword = json.get("passwordTextField").asText();
        emailSubject = json.get("subjectTextField").asText();
        emailBody = json.get("bodyTextField").asText();




        if (emailSubject.length()<1 || emailBody.length()<4|| emailFrom.length()<7|| emailPassword.length()<5) {

            result.put("result", "Please fill all \n The required fields\nCorrectly");

            return CompletableFuture.completedFuture(ok(result));
        }
else{
       

try{

        sendEmail = new SendEmail();
        sendEmail.sendBulkEmail(emailFrom, emailPassword, emailSubject, emailBody);
    System.out.println(emailPassword);

        logger.info("-----------------------------------------------Subject |{}| Body |{}| emailFrom |{}|", emailSubject, emailBody,emailFrom);
  result.put("result", "Success!");
}
catch(Exception e){
   result.put("result","Network Error");
    e.printStackTrace();


}}


        
        // return CompletableFuture.completedFuture(redirect(routes.BranchesController.showBranches()));
        return CompletableFuture.completedFuture(ok(result));
    }

    private long operateOnTempFile(File file) throws IOException {
        final long size = Files.size(file.toPath());
        Files.deleteIfExists(file.toPath());
        return size;
    }

    //  @SubjectPresent
    //@Pattern("branch.edit")
    public CompletionStage<Result> saveJsGridBranch() {
        ObjectNode result = Json.newObject();
        Form<Branch> branchForm = formFactory.form(Branch.class).bindFromRequest();
        Branch branch = branchForm.get();

        recordCount = Branch.finder.query().where().eq("Company_Name", branch.Company_Name).findCount();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        branch.setSelected(Boolean.FALSE);


        if (recordCount > 0) {

            responseCode = "304";
            responseMsg = "Sorry! company with this name : " + branch.Company_Name + " already exists in the Database!";

            result.put("responseCode", responseCode)
                    .put("responseMsg", responseMsg);
            logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++ Response |{}|", responseMsg);

            return CompletableFuture.completedFuture(ok(result));

        } else {

            branch.setCeatedBy(session().get("Username"));
            branch.save();
            return CompletableFuture.completedFuture(ok());
        }
    }

    public CompletionStage<Result> postSaveCompany() {

        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();

        logger.info("######################################## {}", json.toString());

        String Company_Name = json.get("Company_Name").asText();
        String companyCategory = json.get("Company_Category").asText();
        String companySubCategory = json.get("Company_Subcategory").asText();
        String email_1 = json.get("Email_1").asText();
        String email_2 = json.get("Email_2").asText();
        String phone_1 = json.get("Phone_1").asText().replaceFirst("0", "254");
        String phone_2 = json.get("Phone_2").asText().replaceFirst("0", "254");
        String website = json.get("Website").asText();
        String county = json.get("County").asText();
        String town = json.get("Town").asText();
        String Street_Name = json.get("Street_Name").asText();
        String building = json.get("Building").asText();
        String MapLatitude = json.get("MapLatitude").asText();
        String MapLongitude = json.get("MapLongitude").asText();
        String companyBranch = json.get("companyBranch").asText();
        String services = json.get("Services").asText();
        String Status = json.get("Status").asText();
        String comments = json.get("Comments").asText();

        String createdBy = session().get("Username");


        recordCount = Branch.finder.query().where().eq("Website", website).findCount();
        if (recordCount > 0) {

            responseCode = "304";
            responseMsg = "Record exists in the Database!";

            result.put("responseCode", responseCode)
                    .put("responseMsg", responseMsg);

            logger.info("+++++++++++++++++++++++++++++++++++++ Response |{}| ", result.toString());

            return CompletableFuture.completedFuture(ok(result));

        }


        String dateCreated = HeadOfficeController.dateTimeFormatter.format(HeadOfficeController.currentDateTime);

        Branch branch = new Branch(Company_Name, companyCategory, companySubCategory, email_1, email_2, phone_1, phone_2, website,
                county, town, Street_Name, building, MapLatitude, MapLongitude, companyBranch, Status, services, comments, createdBy, dateCreated);

        branch.save();

        responseCode = "200";
        responseMsg = "Saved Successfully";

        result.put("responseCode", responseCode);
        result.put("responseMsg", responseMsg);

        logger.info("+++++++++++++++++++++++++++++++++++++ Response |{}| ", result.toString());

        return CompletableFuture.completedFuture(ok(result));

    }

    public CompletionStage<Result> returnCompanyNameSuggestions() {
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();

        // String userName = json.get("User").asText();
        String businessName = json.get("Company_Name").asText();

        List<String> nameSuggestions = Branch.finder.query().where()
                .contains("Company_Name", businessName)
                .select("Company_Name")
                .findSingleAttributeList();

        responseCode = "200";
        responseMsg = "success";

        result.put("responseCode", responseCode)
                .put("responseMsg", responseMsg)
                .put("suggestions", nameSuggestions.toString());

        return CompletableFuture.completedFuture(ok(result));

    }

    public CompletionStage<Result> returnCompanyData() {
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();

        try {

            String userName = json.get("User").asText();
            String businessName = json.get("Company_Name").asText();

            //
            // You need to add the maven dependency for avaje-ebeanorm-elastic for 'matching' commented below
            Branch company = Branch.finder.query().where().contains("Company_Name", businessName).findOne();

            //Branch company = Branch.finder.query().where().contains("Company_Name", businessName).findOne();

            if (company == null) {

                responseCode = "404";
                responseMsg = "Sorry, Record Not Found";

            } else {

                responseCode = "200";
                responseMsg = "Congratulations!";

                result.put("responseCode", responseCode)
                        .put("responseMsg", responseMsg)

                        .put("Company_Name", company.getCompany_Name())
                        .put("Company_Category", company.getCompany_Category())
                        .put("Company_Subcategory", company.getCompany_Subcategory())
                        .put("Email_1", company.getEmail_1())
                        .put("Email_2", company.getEmail_2())
                        .put("Phone_1", company.getPhone_1())
                        .put("Phone_2", company.getPhone_2())
                        .put("Website", company.getWebsite())
                        .put("County", company.getCounty())
                        .put("Town", company.getTown())
                        .put("Street_Name", company.getStreet_Name())
                        .put("Building", company.getBuilding())
                        .put("MapLatitude", company.getMapLatitude())
                        .put("MapLongitude", company.getMapLongitude())
                        .put("companyBranch", company.getCompany_Branch())
                        .put("Services", company.getServices())
                        .put("Status", company.getStatus())
                        .put("Comments", company.getComments());

            }

            String subject = "RE: HEALTH CHECK " + "\n";
            String body = userName + " Searched for Company Name: " + businessName + "on " + HeadOfficeController.dateTimeFormatter.format(HeadOfficeController.currentDateTime) + " and received \t RESPONSE: " + responseMsg;
            SendEmail.sendHealthCheckEmail(subject, body);

            logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++Email_1 |{}|", subject + body);

            logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++Response |{}|", responseMsg);
        } catch (NullPointerException ex) {
            ex.printStackTrace();

        } catch (NonUniqueResultException ex) {

            responseCode = "405";
            responseMsg = "Sorry, Please narrow down your Quick Search";

            result.put("responseCode", responseCode)
                    .put("responseMsg", responseMsg);
        }

        return CompletableFuture.completedFuture(ok(result));
    }


    //@SubjectPresent
    // @Pattern("branch.edit.approve")
    public CompletionStage<Result> editBranches() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String Id = requestData.get("Id");
        String companyName = requestData.get("Company_Name");
        String companyCategory = requestData.get("Company_Category");
        String companySubCategory = requestData.get("Company_Subcategory");
        String email_1 = requestData.get("Email_1");
        String email_2 = requestData.get("Email_2");
        String phone_1 = requestData.get("Phone_1");
        String phone_2 = requestData.get("Phone_2");
        String website = requestData.get("Website");
        String county = requestData.get("County");
        String town = requestData.get("Town");
        String StreetName = requestData.get("Street_Name");
        String building = requestData.get("Building");
        String mapLatitude = requestData.get("MapLatitude");
        String mapLongitude = requestData.get("MapLongitude");
        String companyBranch = requestData.get("companyBranch");
        String services = requestData.get("Services");
        String Status = requestData.get("Status");
        String comments = requestData.get("Comments");
        String selected = requestData.get("selected");

        logger.info("####################################################APPROVED STATUS {} ", selected);

        Branch oldBranch = Branch.finder.byId(Integer.valueOf(Id));
        if (oldBranch == null) {
            return CompletableFuture.completedFuture(notFound("Not Found"));
        }

        logger.info("####################################################Old Profile{} ", oldBranch.Email_1);

        oldBranch.setCompany_Name(companyName);
        oldBranch.setCompany_Category(companyCategory);
        oldBranch.setCompany_Subcategory(companySubCategory);
        oldBranch.setEmail_1(email_1);
        oldBranch.setEmail_2(email_2);
        oldBranch.setPhone_1(phone_1);
        oldBranch.setPhone_2(phone_2);
        oldBranch.setWebsite(website);
        oldBranch.setCounty(county);
        oldBranch.setTown(town);
        oldBranch.setStreet_Name(StreetName);
        oldBranch.setBuilding(building);
        oldBranch.setMapLatitude(mapLatitude);
        oldBranch.setMapLongitude(mapLongitude);
        oldBranch.setCompany_Branch(companyBranch);
        oldBranch.setServices(services);
        oldBranch.setStatus(Status);
        oldBranch.setComments(comments);
        oldBranch.setSelected(Boolean.parseBoolean(selected));

        oldBranch.setCeatedBy(session().get("Username"));
        oldBranch.setDateCreated(HeadOfficeController.dateTimeFormatter.format(HeadOfficeController.currentDateTime));

        oldBranch.save();
        return CompletableFuture.completedFuture(ok());
    }

    //@Security.Authenticated(Secured.class)
    public CompletionStage<Result> loadBranches() {

        // Executor myEc = HttpExecution.fromThread((Executor) esbExecutionContext);
        
        ObjectNode node=Json.newObject();
        String[] params=new String[24];

        /*selected=&Company_Name=hjhjjhjgjjjjjjjjjjhjg
&Company_Category=ytyujtyuuy&Company_Subcategory=
&Email_1=&Email_2=&Phone_1=&Phone_2=&Website=&County=
&Town=&Street_Name=&Building=&MapLatitude=&MapLongitude=
&companyBranch=&Services=&Status=&Comments=&CreatedBy=&dateCreated=
&pageIndex=1&pageSize=50
*/
        DynamicForm rq = formFactory.form().bindFromRequest();
        params[1]=rq.get("Company_Name");
        params[2]=rq.get("Company_Category");
        params[3]=rq.get("Company_Subcategory");
        params[4]=rq.get("Email_1");
        params[5]=rq.get("Email_2");
        params[6]=rq.get("Phone_1");
        params[7]=rq.get("Phone_2");
        params[8]=rq.get("Website");
        params[9]=rq.get("County");
        params[10]=rq.get("Town");
        params[11]=rq.get("Street_Name");
        params[12]=rq.get("Building");
        params[13]=rq.get("MapLatitude");
        params[14]=rq.get("MapLongitude");
        params[15]=rq.get("companyBranch");
        params[16]=rq.get("Services");
        params[17]=rq.get("Status");
        params[18]=rq.get("Comments");
        params[19]=rq.get("CreatedBy");
        params[20]=rq.get("dateCreated");
        params[21]=rq.get("pageIndex");
        params[22]=rq.get("pageSize");



        node.put("branches",QueryBranches(params));
        node.put("len",len);


        logger.info("Loading branches....for user {} and Branch {} ", session().get("Username"), session().get("branch"));

        //return QueryBranches(s,p).thenApplyAsync(branches -> ok(Json.toJson(branches)), myEc);
        return CompletableFuture.completedFuture(ok(node));
    }


    public  JsonNode QueryBranches(String[] otherParams) {

/*selected=&Company_Name=hjhjjhjgjjjjjjjjjjhjg
&Company_Category=ytyujtyuuy&Company_Subcategory=
&Email_1=&Email_2=&Phone_1=&Phone_2=&Website=&County=
&Town=&Street_Name=&Building=&MapLatitude=&MapLongitude=
&companyBranch=&Services=&Status=&Comments=&CreatedBy=&dateCreated=
&pageIndex=1&pageSize=50
*/


String Company_Name=otherParams[1];
String Company_Category=otherParams[2];
String Company_Subcategory=otherParams[3];
String Email_1=otherParams[4];
String Email_2=otherParams[5];
String Phone_1=otherParams[6];
String Phone_2=otherParams[7];
String Website=otherParams[8];
String County=otherParams[9];
String Town=otherParams[10];
String Street_Name=otherParams[11];
String Building=otherParams[12];
String MapLatitude=otherParams[13];
String MapLongitude=otherParams[14];
String companyBranch=otherParams[15];
String Services=otherParams[16];
String Status=otherParams[17];
String Comments=otherParams[18];
String CreatedBy=otherParams[19];
String dateCreated=otherParams[20];
int pageIndex=Integer.parseInt(otherParams[21]);
int pageSize=Integer.parseInt(otherParams[22]);

len = Branch.find.query().where()
        .ilike("Company_Name", "%"+Company_Name+"%")
        .ilike("Company_Category", "%"+Company_Category+"%")
        .ilike("Company_Subcategory", "%"+Company_Subcategory+"%")
        .ilike("Email_1", "%"+Email_1+"%")
        .ilike("Email_2", "%"+Email_2+"%")
        .ilike("Phone_1", "%"+Phone_1+"%")
        .ilike("Phone_2", "%"+Phone_2+"%")
        .ilike("Website", "%"+Website+"%")
        .ilike("County", "%"+County+"%")
        .ilike("Town", "%"+Town+"%")
        .ilike("Building", "%"+Building+"%")
        .ilike("MapLatitude", "%"+MapLatitude+"%")
        .ilike("MapLongitude", "%"+MapLongitude+"%")
        .ilike("Company_Branch", "%"+companyBranch+"%")
        .ilike("Services", "%"+Services+"%")
        .ilike("Status", "%"+Status+"%")
        .ilike("Comments", "%"+Comments+"%")
        .ilike("CreatedBy", "%"+CreatedBy+"%")
        .ilike("dateCreated", "%"+dateCreated+"%")
        .findCount();

branches = Branch.find.query().where()
        .ilike("Company_Name", "%"+Company_Name+"%")
        .ilike("Company_Category", "%"+Company_Category+"%")
        .ilike("Company_Subcategory", "%"+Company_Subcategory+"%")
        .ilike("Email_1", "%"+Email_1+"%")
        .ilike("Email_2", "%"+Email_2+"%")
        .ilike("Phone_1", "%"+Phone_1+"%")
        .ilike("Phone_2", "%"+Phone_2+"%")
        .ilike("Website", "%"+Website+"%")
        .ilike("County", "%"+County+"%")
        .ilike("Town", "%"+Town+"%")
        .ilike("Building", "%"+Building+"%")
        .ilike("MapLatitude", "%"+MapLatitude+"%")
        .ilike("MapLongitude", "%"+MapLongitude+"%")
        .ilike("Company_Branch", "%"+companyBranch+"%")
        .ilike("Services", "%"+Services+"%")
        .ilike("Status", "%"+Status+"%")
        .ilike("Comments", "%"+Comments+"%")
        .ilike("CreatedBy", "%"+CreatedBy+"%")
        .ilike("dateCreated", "%"+dateCreated+"%")
        .orderBy("Company_Name asc")
        .setFirstRow(pageIndex-1)
        .setMaxRows(pageSize)
        .findPagedList()
        .getList();


        String userRoleName = session().get("RoleName");



        logger.info("+++++++++++++++++++++++++++++++++++++++++++ RoleName |{}|", userRoleName);


        return Json.toJson(branches);

    }


    public CompletionStage<Result> postDelete() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String Id = requestData.get("Id");

        Branch oldBranch = Branch.finder.byId(Integer.parseInt(Id));
        if (oldBranch == null) {
            logger.info("The Requested User is Null+=== User++++{}++++ Creator +++++{}", requestData.get("id"), session("Username"));
            return CompletableFuture.completedFuture(notFound());
        }
        oldBranch.delete();
        logger.info("The Requested User has been successfully Deleted UserNumber++++{}++++ DeletedBy +++++{}", requestData.get("mobile_number"), session("Username"));

        return CompletableFuture.completedFuture(ok());
    }

    public CompletionStage<Result> postDeleteSelected() {
        //DynamicForm requestData = formFactory.form().bindFromRequest();
        // String Id = requestData.get("Id");

        List<Branch> selectedBusinesses = Branch.finder.query().where().eq("selected", Boolean.TRUE).findList();

        Ebean.beginTransaction();
        Ebean.deleteAll(selectedBusinesses);
        Ebean.commitTransaction();

        if (selectedBusinesses == null) {
            //logger.info("The Requested User is Null+=== User++++{}++++ Creator +++++{}", requestData.get("id"), session("Username"));
            return CompletableFuture.completedFuture(notFound());
        }
        Ebean.deleteAll(selectedBusinesses);
        // logger.info("The Requested User has been successfully Deleted UserNumber++++{}++++ DeletedBy +++++{}", requestData.get("mobile_number"), session("Username"));

        return CompletableFuture.completedFuture(redirect(routes.BranchesController.showBranches()));
    }

    public CompletionStage<Result> postDeleteAll() {
        try {

            List<Branch> oldBusinesses = Branch.finder.all();
            Ebean.beginTransaction();
            Ebean.deleteAll(oldBusinesses);
            Ebean.commitTransaction();

            if (oldBusinesses == null) {
                ////  logger.info("The Requested User is Null+=== User++++{}++++ Creator +++++{}", requestData.get("id"), session("Username"));
                return CompletableFuture.completedFuture(notFound());
            }
            // logger.info("The Requested User has been successfully Deleted UserNumber++++{}++++ DeletedBy +++++{}", requestData.get("mobile_number"), session("Username"));

        } catch (Exception e) {
            // Ebean.endTransaction();
            e.printStackTrace();

        }

        return CompletableFuture.completedFuture(redirect(routes.BranchesController.showBranches()));
    }


    public CompletionStage<Result> generateExcelReport() {
        try {
            List<Branch> branches = Branch.finder.all().subList(1, 5);


            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("CompanyDirectory2");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.BRIGHT_GREEN.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Create a Row
            org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);

            for (int i = 0; i < sheetColumns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(sheetColumns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            // Create Other rows and cells with contacts data
            int rowNum = 1;

            for (Branch business : branches) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(business.Company_Name);
                row.createCell(1).setCellValue(business.Company_Category);
                row.createCell(2).setCellValue(business.Company_Subcategory);
                row.createCell(3).setCellValue(business.Email_1);
                row.createCell(4).setCellValue(business.Email_2);
                row.createCell(5).setCellValue(business.Phone_1);
                row.createCell(6).setCellValue(business.Phone_2);
                row.createCell(7).setCellValue(business.Website);
                row.createCell(8).setCellValue(business.County);
                row.createCell(9).setCellValue(business.Town);
                row.createCell(10).setCellValue(business.Street_Name);
                row.createCell(11).setCellValue(business.Building);
                row.createCell(12).setCellValue(business.MapLatitude);
                row.createCell(13).setCellValue(business.MapLongitude);
                row.createCell(14).setCellValue(business.Company_Branch);
                row.createCell(15).setCellValue(business.Status);
                row.createCell(16).setCellValue(business.Services);
                row.createCell(17).setCellValue(business.Comments);
            }

            // Resize all columns to fit the content size
            for (int i = 0; i < sheetColumns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the output to a file
            FileOutputStream fileOut = null;

            fileOut = new FileOutputStream("CompanyDirectory.xlsx");

            workbook.write(fileOut);
            fileOut.close();

            return CompletableFuture.completedFuture(redirect(routes.BranchesController.showBranches()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(redirect(routes.BranchesController.showBranches()));


    }

    public CompletableFuture<Result> generatePDFReport() {


// Load your XLSX file in Workbook object


        return CompletableFuture.completedFuture(redirect(routes.BranchesController.showBranches()));

    }

public static String sessionName(){

String usename=session().get("Username");
return usename;
}

}
