package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbclient")
public class TBClient extends Model {

    @Id
    private Integer Id;
    private String FirstName;
    private String MiddleName;
    private String lastName;
    private String PhoneNumber;
    private String MonthlyIncome;
    private String Email;
    private String Location;
    private String IdentificationNumber;
    private String IDType;
    private String FaceImage;
    private String IDImage;
    private String Fingerprint;
    private String Dob;
    private boolean IsSacco;
    private String ClientID;

    public static Finder<Integer, TBClient> finder=new Finder<>(TBClient.class);

    public TBClient(String firstName, String middleName, String lastName, String phoneNumber, String monthlyIncome, String email, String location, String identificationNumber, String IDType, String faceImage, String IDImage, String fingerprint, String dob, boolean isSacco, String clientID) {
        FirstName = firstName;
        MiddleName = middleName;
        this.lastName = lastName;
        PhoneNumber = phoneNumber;
        MonthlyIncome = monthlyIncome;
        Email = email;
        Location = location;
        IdentificationNumber = identificationNumber;
        this.IDType = IDType;
        FaceImage = faceImage;
        this.IDImage = IDImage;
        Fingerprint = fingerprint;
        Dob = dob;
        IsSacco = isSacco;
        ClientID = clientID;
    }

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
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getMonthlyIncome() {
        return MonthlyIncome;
    }

    public void setMonthlyIncome(String monthlyIncome) {
        MonthlyIncome = monthlyIncome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getIdentificationNumber() {
        return IdentificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        IdentificationNumber = identificationNumber;
    }

    public String getIDType() {
        return IDType;
    }

    public void setIDType(String IDType) {
        this.IDType = IDType;
    }

    public String getFaceImage() {
        return FaceImage;
    }

    public void setFaceImage(String faceImage) {
        FaceImage = faceImage;
    }

    public String getIDImage() {
        return IDImage;
    }

    public void setIDImage(String IDImage) {
        this.IDImage = IDImage;
    }

    public String getFingerprint() {
        return Fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        Fingerprint = fingerprint;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public boolean isSacco() {
        return IsSacco;
    }

    public void setSacco(boolean sacco) {
        IsSacco = sacco;
    }

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String clientID) {
        ClientID = clientID;
    }
}
