package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "tbindividualemails")
public class IndividualEmails extends Model {


    @Id
    public Integer emailId;
    public String individualEmail;
    public String individualDescription;
    public String individualComments;
    public String individualCreatedBy;
    public String individualDateCreated;
    public boolean individualSelected;

    public IndividualEmails(String email, String description, String comments, String createdBy, String dateCreated) {
        individualEmail = email;
        individualDescription = description;
        individualComments = comments;
        individualCreatedBy = createdBy;
        individualDateCreated = dateCreated;
    }




    public static Finder<Integer, IndividualEmails> finder = new Finder<>(IndividualEmails.class);

    public Integer getEmailId() {
        return emailId;
    }

    public void setEmailId(Integer emailId) {
        this.emailId = emailId;
    }

    public String getEmail() {
        return individualEmail;
    }

    public void setEmail(String email) {
        individualEmail = email;
    }

    public String getDescription() {
        return individualDescription;
    }

    public void setDescription(String description) {
        individualDescription = description;
    }

    public String getComments() {
        return individualComments;
    }

    public void setComments(String comments) {
        individualComments = comments;
    }

    public String getCreatedBy() {
        return individualCreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        individualCreatedBy = createdBy;
    }

    public String getDateCreated() {
        return individualDateCreated;
    }

    public void setDateCreated(String dateCreated) {
        individualDateCreated = dateCreated;
    }

    public boolean isSelected() {
        return individualSelected;
    }

    public void setSelected(boolean selected) {
        individualSelected= selected;
    }


}
