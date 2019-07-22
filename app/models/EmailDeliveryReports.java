package models;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbemaildeliveryreports")
public class EmailDeliveryReports extends Model {

    @Id
    public Integer Id;
    public String emailFrom;
    public String emailTo;


    public boolean delivered;
    public String sentBy;
    public String dateSent;

    public EmailDeliveryReports(String emailFrom, String emailTo, boolean delivered, String sentBy, String dateSent) {
        //Id = id;
        this.emailFrom = emailFrom;
        this.emailTo = emailTo;
        this.delivered = delivered;
        this.sentBy = sentBy;
        this.dateSent = dateSent;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public String getDateSent() {
        return dateSent;
    }

    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }

}
