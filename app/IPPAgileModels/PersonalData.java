package IPPAgileModels;

import io.ebean.Finder;
import io.ebean.Model;
import models.Branch;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbpersonaldata")
public class PersonalData extends Model {
    @Id
    private Integer Id;
    private String FirstName;
    private String MiddleName;
    private String LastName;
    private String MobileNumber;
    private String OfficeTelephone;
    private String IDNumber;
    private String DateOfBirth;
    private String EmailAddress;
    private String PostalAddress;
    private String Employer;
    private String Title;


    public PersonalData(Integer id, String firstName, String middleName, String lastName, String mobileNumber, String officeTelephone,
                        String IDNumber, String dateOfBirth, String emailAddress, String postalAddress, String employer, String title) {
        Id = id;
        FirstName = firstName;
        MiddleName = middleName;
        LastName = lastName;
        MobileNumber = mobileNumber;
        OfficeTelephone = officeTelephone;
        this.IDNumber = IDNumber;
        DateOfBirth = dateOfBirth;
        EmailAddress = emailAddress;
        PostalAddress = postalAddress;
        Employer = employer;
        Title = title;
    }

    public static Finder<Integer, PersonalData> finder = new Finder<>(PersonalData.class);


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getOfficeTelephone() {
        return OfficeTelephone;
    }

    public void setOfficeTelephone(String officeTelephone) {
        OfficeTelephone = officeTelephone;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getPostalAddress() {
        return PostalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        PostalAddress = postalAddress;
    }

    public String getEmployer() {
        return Employer;
    }

    public void setEmployer(String employer) {
        Employer = employer;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }


}
