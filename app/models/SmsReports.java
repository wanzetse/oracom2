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
public String SentBy;
public String SentTo;
public boolean received;
public String SentDate;
public String DateReceived;
public SmsReports(){

}

public static Finder<Long,SmsReports> find =new Finder<>(SmsReports.class);
}