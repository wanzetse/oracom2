package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbpersonsbyregion")
public class PersonsByRegion extends Model {

    @Id
    public Integer Id;
    private String person_phone;
    private String person_Surname;
    private String person_Othernames;
    private String person_CountyName;
    private String person_Constituency_name;
    private String person_WardName;
    private String person_PollingName;
    private String person_Email_address;
    private String person_Gender;
    private String person_comment;
    private String person_CreateDate;
    private String person_CreatedBy;
    private boolean personSelected;

    public PersonsByRegion(String person_phone, String person_Surname, String person_Othernames, String person_CountyName, String person_Constituency_name,
                           String person_WardName, String person_PollingName, String person_Email_address, String person_Gender, String person_comment,
                           String person_CreateDate, String person_CreatedBy) {
        this.person_phone = person_phone;
        this.person_Surname = person_Surname;
        this.person_Othernames = person_Othernames;
        this.person_CountyName = person_CountyName;
        this.person_Constituency_name = person_Constituency_name;
        this.person_WardName = person_WardName;
        this.person_PollingName = person_PollingName;
        this.person_Email_address = person_Email_address;
        this.person_Gender = person_Gender;
        this.person_comment = person_comment;
        this.person_CreateDate = person_CreateDate;
        this.person_CreatedBy = person_CreatedBy;
        //this.personSelected = personSelected;
    }

    public static Finder<Integer, PersonsByRegion> finder = new Finder<>(PersonsByRegion.class);

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getPerson_phone() {
        return person_phone;
    }

    public void setPerson_phone(String person_phone) {
        this.person_phone = person_phone;
    }

    public String getPerson_Surname() {
        return person_Surname;
    }

    public void setPerson_Surname(String person_Surname) {
        this.person_Surname = person_Surname;
    }

    public String getPerson_Othernames() {
        return person_Othernames;
    }

    public void setPerson_Othernames(String person_Othernames) {
        this.person_Othernames = person_Othernames;
    }

    public String getPerson_CountyName() {
        return person_CountyName;
    }

    public void setPerson_CountyName(String person_CountyName) {
        this.person_CountyName = person_CountyName;
    }

    public String getPerson_Constituency_name() {
        return person_Constituency_name;
    }

    public void setPerson_Constituency_name(String person_Constituency_name) {
        this.person_Constituency_name = person_Constituency_name;
    }

    public String getPerson_WardName() {
        return person_WardName;
    }

    public void setPerson_WardName(String person_WardName) {
        this.person_WardName = person_WardName;
    }

    public String getPerson_PollingName() {
        return person_PollingName;
    }

    public void setPerson_PollingName(String person_PollingName) {
        this.person_PollingName = person_PollingName;
    }

    public String getPerson_Email_address() {
        return person_Email_address;
    }

    public void setPerson_Email_address(String person_Email_address) {
        this.person_Email_address = person_Email_address;
    }

    public String getPerson_Gender() {
        return person_Gender;
    }

    public void setPerson_Gender(String person_Gender) {
        this.person_Gender = person_Gender;
    }

    public String getPerson_comment() {
        return person_comment;
    }

    public void setPerson_comment(String person_comment) {
        this.person_comment = person_comment;
    }

    public String getPerson_CreateDate() {
        return person_CreateDate;
    }

    public void setPerson_CreateDate(String person_CreateDate) {
        this.person_CreateDate = person_CreateDate;
    }

    public String getPerson_CreatedBy() {
        return person_CreatedBy;
    }

    public void setPerson_CreatedBy(String person_CreatedBy) {
        this.person_CreatedBy = person_CreatedBy;
    }

    public boolean isPersonSelected() {
        return personSelected;
    }

    public void setPersonSelected(boolean personSelected) {
        this.personSelected = personSelected;
    }


}
