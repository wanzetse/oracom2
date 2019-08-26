package modules;
import javax.persistence.*;
import controllers.BranchesController;
import models.*;
import io.ebean.Ebean;
import java.util.*;
import io.ebean.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import play.mvc.Http;
import io.ebean.Model;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Locale;

import static org.hibernate.util.ConfigHelper.getResourceAsStream;

public class ExcelDataConfig {
    public static HSSFWorkbook wb;
    public static HSSFSheet sheet;
    private static   EbeanServer server = Ebean.getServer(null);

    public static String[] sheetColumns = {"Company_Name", "Company_Category", "Company_Subcategory", "Email_1", "Email_2",
            "Phone_1", "Phone_2", "Website", "County", "Town", "Street_Name", "Building", "Latitude",
            "Longitude", "companyBranch", "Status", "Services", "Comments", "CreatedBy", "DateCreated"
    };

    public ExcelDataConfig(String excelPath) {
        try {
            File src = new File(excelPath);
            FileInputStream fis = new FileInputStream(src);
            wb = new HSSFWorkbook(fis);
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

    }
 static DataFormatter formatter = new DataFormatter(Locale.UK);
  private static String getSheetvalue(org.apache.poi.xssf.usermodel.XSSFCell val){
    
   
    String s=formatter.formatCellValue(val);
    String s2="";
    if(s.length()<1){
                s2="null";
            }else{
                s2=s.trim().replaceAll("\\s+", " ");
               
            }
            return s2;

        }

@play.db.ebean.Transactional
    public static void readExcel(File uploadedFile, String createdBy, String dateCreated) throws IOException {


        

        InputStream file = new FileInputStream(new File(uploadedFile.getPath()));

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
        List<Branch> newBranch=new ArrayList<>();

       for(int i=0;i<sheet.getLastRowNum();i++){
         Branch oldBranch=new Branch(
            getSheetvalue(sheet.getRow(i).getCell(0)),
            getSheetvalue(sheet.getRow(i).getCell(1)),
            getSheetvalue(sheet.getRow(i).getCell(2)),
            getSheetvalue(sheet.getRow(i).getCell(3)),
            getSheetvalue(sheet.getRow(i).getCell(4)),
            getSheetvalue(sheet.getRow(i).getCell(5)),
            getSheetvalue(sheet.getRow(i).getCell(6)),
            getSheetvalue(sheet.getRow(i).getCell(7)),
            getSheetvalue(sheet.getRow(i).getCell(8)),
            getSheetvalue(sheet.getRow(i).getCell(9)),
            getSheetvalue(sheet.getRow(i).getCell(10)),
            getSheetvalue(sheet.getRow(i).getCell(11)),
            getSheetvalue(sheet.getRow(i).getCell(12)),
            getSheetvalue(sheet.getRow(i).getCell(13)),
            getSheetvalue(sheet.getRow(i).getCell(14)),
            getSheetvalue(sheet.getRow(i).getCell(15)),
            getSheetvalue(sheet.getRow(i).getCell(16)),
            getSheetvalue(sheet.getRow(i).getCell(17)),
            getSheetvalue(sheet.getRow(i).getCell(18)),
            getSheetvalue(sheet.getRow(i).getCell(19))
 );
         //oldBranch.save();

        newBranch.add(oldBranch);


         }
     
          

        try {
            Transaction transaction=server.beginTransaction();
            transaction.setBatchMode(true);
            transaction.setBatchSize(5000);
            server.saveAll(newBranch);
            transaction.commit();

            file.close();

        } catch (Exception e1) {
            e1.printStackTrace();
        }


    }
  @play.db.ebean.Transactional
    public static void readExcelLeaders(File uploadedFile, String createdBy, String dateCreated) throws IOException {


        DataFormatter formatter = new DataFormatter(Locale.UK);

        InputStream file = new FileInputStream(new File(uploadedFile.getPath()));

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);

        XSSFRow row;
        List<Leaders> oldleader=new ArrayList<>();
        for (int i = 1; i < sheet.getLastRowNum(); i++) {  //points to the starting of excel i.e excel first row
            Leaders leader = new Leaders(
            getSheetvalue(sheet.getRow(i).getCell(0)),
            getSheetvalue(sheet.getRow(i).getCell(1)),
            getSheetvalue(sheet.getRow(i).getCell(2)),
            getSheetvalue(sheet.getRow(i).getCell(3)),
            getSheetvalue(sheet.getRow(i).getCell(4)),
            getSheetvalue(sheet.getRow(i).getCell(5)),
            getSheetvalue(sheet.getRow(i).getCell(6)),
            getSheetvalue(sheet.getRow(i).getCell(7)),
            getSheetvalue(sheet.getRow(i).getCell(8))
            


                );

            
            oldleader.add(leader);

            //log the data
            BranchesController.logger.info("####################################################UPLOADED BY {} ", createdBy);


        }

        try {

            Transaction transaction=server.beginTransaction();
            transaction.setBatchMode(true);
            transaction.setBatchSize(5000);
            server.saveAll(oldleader);
            transaction.commit();

            file.close();

        } catch (Exception e1) {
            e1.printStackTrace();
        }


    }
  @play.db.ebean.Transactional
    public static void importExcelPersonsData(File uploadedFile, String createdBy, String dateCreated) throws IOException {


        DataFormatter formatter = new DataFormatter(Locale.UK);

        InputStream file = new FileInputStream(new File(uploadedFile.getPath()));

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);

        List<HeadOffice> hdf=new ArrayList<>();


        XSSFRow row;

        for (int i = 1; i < sheet.getLastRowNum(); i++) {  //points to the starting of excel i.e excel first row
             HeadOffice person = new HeadOffice(
            getSheetvalue(sheet.getRow(i).getCell(0)),
            getSheetvalue(sheet.getRow(i).getCell(1)),
            getSheetvalue(sheet.getRow(i).getCell(2)),
            getSheetvalue(sheet.getRow(i).getCell(3)),
            getSheetvalue(sheet.getRow(i).getCell(4)),
            getSheetvalue(sheet.getRow(i).getCell(5)),
            getSheetvalue(sheet.getRow(i).getCell(6)),
            getSheetvalue(sheet.getRow(i).getCell(7)),
            getSheetvalue(sheet.getRow(i).getCell(8)),
            getSheetvalue(sheet.getRow(i).getCell(9)),
            getSheetvalue(sheet.getRow(i).getCell(10)),
            getSheetvalue(sheet.getRow(i).getCell(11)),
            getSheetvalue(sheet.getRow(i).getCell(12))
            );

            //person.save();
            hdf.add(person);

            // logger.info("####################################################APPROVED STATUS {} ", website);

        }

        try {
            Transaction transaction=server.beginTransaction();
            transaction.setBatchMode(true);
            transaction.setBatchSize(5000);
            server.saveAll(hdf);
            transaction.commit();

            file.close();

        } catch (Exception e1) {
            e1.printStackTrace();
        }


    }
  @play.db.ebean.Transactional
    public static void importExcelCorporateEmails(File uploadedFile, String createdBy, String dateCreated) throws IOException {


        DataFormatter formatter = new DataFormatter(Locale.UK);

        InputStream file = new FileInputStream(new File(uploadedFile.getPath()));

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);

        XSSFRow row;
        List<CorporateEmails> oldCpemails=new ArrayList<>();
        for (int i = 1; i < sheet.getLastRowNum(); i++) {  //

            CorporateEmails corporateEmails = new CorporateEmails(
            getSheetvalue(sheet.getRow(i).getCell(0)),
            getSheetvalue(sheet.getRow(i).getCell(1)),
            getSheetvalue(sheet.getRow(i).getCell(2)),
            getSheetvalue(sheet.getRow(i).getCell(3)),
            getSheetvalue(sheet.getRow(i).getCell(4)));
            oldCpemails.add(corporateEmails);

            //corporateEmails.save();

            // logger.info("####################################################APPROVED STATUS {} ", website);

        }

        try {

            Transaction transaction=server.beginTransaction();
            transaction.setBatchMode(true);
            transaction.setBatchSize(5000);
            server.saveAll(oldCpemails);
            transaction.commit();

            file.close();

        } catch (Exception e1) {
            e1.printStackTrace();
        }


    }
  @play.db.ebean.Transactional
    public static void importExcelIndividualEmails(File uploadedFile, String createdBy, String dateCreated) throws IOException {


        DataFormatter formatter = new DataFormatter(Locale.UK);

        InputStream file = new FileInputStream(new File(uploadedFile.getPath()));

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
        


         List<IndividualEmails> emails=new ArrayList<>();

         for (int i = 1; i < sheet.getLastRowNum(); i++) { 
            IndividualEmails individualEmails = new IndividualEmails(
            getSheetvalue(sheet.getRow(i).getCell(0)),
            getSheetvalue(sheet.getRow(i).getCell(1)),
            getSheetvalue(sheet.getRow(i).getCell(2)),
            getSheetvalue(sheet.getRow(i).getCell(3)),
            getSheetvalue(sheet.getRow(i).getCell(4)));

            //individualEmails.save();
            emails.add(individualEmails);

            // logger.info("####################################################APPROVED STATUS {} ", website);

        }

        try {
            Transaction transaction=server.beginTransaction();
            transaction.setBatchMode(true);
            transaction.setBatchSize(5000);
            server.saveAll(emails);
            transaction.commit();

            file.close();

        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }
  @play.db.ebean.Transactional
    public static void importExcelIndividualPhoneNumbers(File uploadedFile, String createdBy, String dateCreated) throws IOException {


        DataFormatter formatter = new DataFormatter(Locale.UK);

        InputStream file = new FileInputStream(new File(uploadedFile.getPath()));

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);

        List<Phones> phones=new ArrayList<>();

        XSSFRow row;
        for (int i = 1; i < sheet.getLastRowNum(); i++) {  //points to the starting of excel i.e excel first row
            
            Phones phoneNumbers = new Phones(
            getSheetvalue(sheet.getRow(i).getCell(0)),
            getSheetvalue(sheet.getRow(i).getCell(1)),
            getSheetvalue(sheet.getRow(i).getCell(2)),
            getSheetvalue(sheet.getRow(i).getCell(3)),
            getSheetvalue(sheet.getRow(i).getCell(4))
                );

            //phoneNumbers.save();
            phones.add(phoneNumbers);

            // logger.info("####################################################APPROVED STATUS {} ", website);

        }

        try {
            Transaction transaction=server.beginTransaction();
            transaction.setBatchMode(true);
            transaction.setBatchSize(5000);
            server.saveAll(phones);
            transaction.commit();

            file.close();

        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }
  @play.db.ebean.Transactional
    public static void importExcelPersonsByRegion(File uploadedFile, String createdBy, String dateCreated) throws IOException {


        DataFormatter formatter = new DataFormatter(Locale.UK);

        InputStream file = new FileInputStream(new File(uploadedFile.getPath()));

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
        List<PersonsByRegion> psg=new ArrayList<>();


        XSSFRow row;
        for (int i = 1; i < sheet.getLastRowNum(); i++) {  //points to the starting of excel i.e excel first row
            PersonsByRegion persons = new PersonsByRegion(
            getSheetvalue(sheet.getRow(i).getCell(0)),
            getSheetvalue(sheet.getRow(i).getCell(1)),
            getSheetvalue(sheet.getRow(i).getCell(2)),
            getSheetvalue(sheet.getRow(i).getCell(3)),
            getSheetvalue(sheet.getRow(i).getCell(4)),
            getSheetvalue(sheet.getRow(i).getCell(5)),
            getSheetvalue(sheet.getRow(i).getCell(6)),
            getSheetvalue(sheet.getRow(i).getCell(7)),
            getSheetvalue(sheet.getRow(i).getCell(8)),
            getSheetvalue(sheet.getRow(i).getCell(9)),
            getSheetvalue(sheet.getRow(i).getCell(10)),
            getSheetvalue(sheet.getRow(i).getCell(11))
            );

           // persons.save();
            psg.add(persons);

            // logger.info("####################################################APPROVED STATUS {} ", website);

        }

        try {
            Transaction transaction=server.beginTransaction();
            transaction.setBatchMode(true);
            transaction.setBatchSize(5000);
            server.saveAll(psg);
            transaction.commit();

            file.close();

        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    public static String getData(int sheetNumber, int row, int column) {
        DataFormatter formatter = new DataFormatter();

        sheet = wb.getSheetAt(sheetNumber);
        String data1 = formatter.formatCellValue(sheet.getRow(row).getCell(column));
        //  String data =sheet.getRow(row).getCell(column).getStringCellValue();
        //  return data;
        return data1;

    }
/*
    public  File convertExcelToPDF(String filepath){

        InputStream in = getResourceAsStream(filepath);
        PrintWriter out = new PrintWriter(new FileWriter("./test-xlsx.html"));

        // this class is based on code found at
        // https://svn.apache.org/repos/asf/poi/trunk/src/examples/src/org/apache/poi/ss/examples/html/ToHtml.java
        // and will convert .xlsx files
        ExcelToHtmlConverter toHtml = ExcelToHtmlConverter.create(in, out);
        toHtml.setCompleteHTML(true);
        toHtml.printPage();


        // rather than writing to file get the HTML in memory and use
        // FlyingSaucer or OpenHTMlToPdf

        in.close();
    }
    */
}
