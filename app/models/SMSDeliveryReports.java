package models;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbsmsdeliveryreports")
public class SMSDeliveryReports extends Model {
    @Id
    public Integer Id;
    public String SENDER_ID;
    public String SMS_to;
    public boolean delivered;
    public String sentBy;
    public String dateSent;

}
