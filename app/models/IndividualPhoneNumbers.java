package models;

import io.ebean.Finder;
import io.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbphones")
public class IndividualPhoneNumbers extends Model {


    @Id
    private Integer Id;
    private String individual_phone;
    private String individualPhone_status;
    private String individualPhone_Comments;
    private String individualPhone_CreatedBy;
    private String individualPhone_dateCreated;
    private boolean individualPhone_selected;


    public IndividualPhoneNumbers(String Id, String individual_phone, String individualPhone_status, String individualPhone_Comments, String individualPhone_CreatedBy,
                                  String individualPhone_dateCreated) {
        this.individual_phone = individual_phone;
        this.individualPhone_status = individualPhone_status;
        this.individualPhone_Comments = individualPhone_Comments;
        this.individualPhone_CreatedBy = individualPhone_CreatedBy;
        this.individualPhone_dateCreated = individualPhone_dateCreated;
        // this.individualPhone_selected = individualPhone_selected;
    }

    public static Finder<Integer, IndividualPhoneNumbers> finder = new Finder<>(IndividualPhoneNumbers.class);

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getIndividual_phone() {
        return individual_phone;
    }

    public void setIndividual_phone(String individual_phone) {
        this.individual_phone = individual_phone;
    }

    public String getIndividualPhone_status() {
        return individualPhone_status;
    }

    public void setIndividualPhone_status(String individualPhone_status) {
        this.individualPhone_status = individualPhone_status;
    }

    public String getIndividualPhone_Comments() {
        return individualPhone_Comments;
    }

    public void setIndividualPhone_Comments(String individualPhone_Comments) {
        this.individualPhone_Comments = individualPhone_Comments;
    }

    public String getIndividualPhone_CreatedBy() {
        return individualPhone_CreatedBy;
    }

    public void setIndividualPhone_CreatedBy(String individualPhone_CreatedBy) {
        this.individualPhone_CreatedBy = individualPhone_CreatedBy;
    }

    public String getIndividualPhone_dateCreated() {
        return individualPhone_dateCreated;
    }

    public void setIndividualPhone_dateCreated(String individualPhone_dateCreated) {
        this.individualPhone_dateCreated = individualPhone_dateCreated;
    }

    public boolean isIndividualPhone_selected() {
        return individualPhone_selected;
    }

    public void setIndividualPhone_selected(boolean individualPhone_selected) {
        this.individualPhone_selected = individualPhone_selected;
    }


}
