package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;

@Entity
@Table(name = "tbperson")
public class HeadOffice extends Model {


    public String Company;
    public String Full_Names;
    public String Email_1;
    public String Email_2;
    public String Phone_1;
    public String Phone_2;
    public String Position;
    public String SideHustle;
    public String Sex;
    public String Status;
    public String Comments;
    public String CreatedBy;
    public String dateCreated;
    public boolean selected;


    public HeadOffice(Integer id, String fullNames, String email2, String phone2, String company, String email1, String phone1, String position, String sex, String CreatedBy, String date) {
        Id = id;
        Full_Names = fullNames;
        Email_2 = email2;
        Phone_2 = phone2;
        Company = company;
        Email_1 = email1;
        Phone_1 = phone1;
        Position = position;
        Sex = sex;
        this.CreatedBy = CreatedBy;
        dateCreated = date;
    }

    public HeadOffice(String fullNames, String email2, String phone2, String company, String email1, String phone1, String position,
                      String sideHustle, String sex, String status, String comments, String createdBy, String date) {
        Company = company;
        Full_Names = fullNames;
        Email_2 = email2;
        Email_1 = email1;
        Phone_2 = phone2;
        Phone_1 = phone1;
        Position = position;
        SideHustle = sideHustle;
        Sex = sex;
        Status = status;
        Comments = comments;
        CreatedBy = createdBy;
        dateCreated = date;
    }

  /*
    @ManyToMany
    private List<Business> bizOffice;
*/

    public static Finder<Integer, HeadOffice> finder = new Finder<>(HeadOffice.class);

    @Id
    public Integer Id;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getFull_Names() {
        return Full_Names;
    }

    public void setFull_Names(String full_Names) {
        Full_Names = full_Names;
    }

    public String getEmail_1() {
        return Email_1;
    }

    public void setEmail_1(String email_1) {
        Email_1 = email_1;
    }

    public String getEmail_2() {
        return Email_2;
    }

    public void setEmail_2(String email_2) {
        Email_2 = email_2;
    }

    public String getPhone_1() {
        return Phone_1;
    }

    public void setPhone_1(String phone_1) {
        Phone_1 = phone_1;
    }

    public String getPhone_2() {
        return Phone_2;
    }

    public void setPhone_2(String phone_2) {
        Phone_2 = phone_2;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getSideHustle() {
        return SideHustle;
    }

    public void setSideHustle(String sideHustle) {
        SideHustle = sideHustle;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
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
/*
    public List<Business> getBusiness() {
        return bizOffice;
    }

    public void setBusiness(List<Business> business) {
        this.bizOffice = business;
    }
    */
}
