package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbcorporateemails")
public class CorporateEmails extends Model {

    @Id
    public Integer Id;
    public String Email;
    public String Description;
    public String Comments;
    public String CreatedBy;
    public String dateCreated;
    public boolean selected;


    public CorporateEmails(String email, String description, String comments, String createdBy, String dateCreated) {
        Email = email;
        Description = description;
        Comments = comments;
        CreatedBy = createdBy;
        this.dateCreated = dateCreated;
        //this.selected = selected;
    }


    public static Finder<Integer, CorporateEmails> finder = new Finder<>(CorporateEmails.class);

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


}
