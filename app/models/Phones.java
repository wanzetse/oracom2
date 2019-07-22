package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbbusinesscategory")
public class Phones extends Model {

    @Id
    public Integer id;
    public String individual_phone;
    private String individualPhone_status;
    private String individualPhone_Comments;
    private String CreateDate;
    public String CreatedBy;
   // public boolean Active;
  //  public boolean phoneIsSelected;
   // public String ApprovedDate;
    //public String ApprovedBy;

    public static Finder<Integer, Phones> finder = new Finder<>(Phones.class);

    public Phones(String phone, String status, String comments, String createDate, String createdBy) {
        individual_phone = phone;
        individualPhone_status = status;
        individualPhone_Comments = comments;
        CreateDate = createDate;
        CreatedBy = createdBy;
      //  phoneIsSelected = selected;
       // ApprovedDate = approvedDate;
        //ApprovedBy = approvedBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }
/*
    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }


    public boolean isPhoneIsSelected() {
        return phoneIsSelected;
    }

    public void setPhoneIsSelected(boolean phoneIsSelected) {
        this.phoneIsSelected = phoneIsSelected;
    }

    public String getApprovedDate() {
        return ApprovedDate;
    }

    public void setApprovedDate(String approvedDate) {
        ApprovedDate = approvedDate;
    }

    public String getApprovedBy() {
        return ApprovedBy;
    }

    public void setApprovedBy(String approvedBy) {
        ApprovedBy = approvedBy;
    }
    */
}
