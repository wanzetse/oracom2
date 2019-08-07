package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "tbEmailReports")
public class EmailReports extends Model{
@Id 
public Long id;
public String SentBy;
public String SentTo;
public boolean received;
public String SentDate;
public String DateReceived;
public EmailReports(){

}
public static Finder<Long,EmailReports> find =new Finder<>(EmailReports.class);
}