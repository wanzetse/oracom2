package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "tbSmsReports")
public class SmsReports extends Model{
@Id 
public Long id;
public String type;
public String SenderName;
public String SentBy;
public String SentTo;
public boolean received;
public String SentDate;
public String DateReceived;
public String message;
public SmsReports(){

}
public SmsReports(String type,String SenderName,String SentBy,String SentTo,boolean received,String SentDate,String DateReceived,String message){
	this.type=type;
	this.SenderName=SenderName;
	this.SentBy=SentBy;
	this.SentTo=SentTo;
	this.received=received;
	this.SentDate=SentDate;
	this.DateReceived=DateReceived;
	this.message=message;
}
public static Finder<Long,SmsReports> find =new Finder<>(SmsReports.class);
}