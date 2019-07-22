package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbcustomer")
public class TBCustomer extends Model {

    @Id
    private Integer id;
    private String FirstName;
    private String MiddleName;
    private String LastName;
    private String PhoneNumber;
    private String Location;
    private String Gender;
    private String DOB;
    private String IDNumber;
    private String IDType;
    private String FaceImage;
    private String IDImage;
    private String Fingerprint;
    private String CreatedBy;
    private String CreateDate;
    private boolean IsApproved;
    private String ApprovedBy;
    private String ApproveDate;

    public static Finder<Integer, TBCustomer> finder=new Finder<>(TBCustomer.class);

    public TBCustomer(String firstName, String middleName, String lastName, String phoneNumber, String location, String gender, String DOB, String IDNumber, String IDType, String faceImage, String IDImage, String fingerprint, String createdBy, String createDate, boolean isApproved, String approvedBy, String approveDate) {
        FirstName = firstName;
        MiddleName = middleName;
        LastName = lastName;
        PhoneNumber = phoneNumber;
        Location = location;
        Gender = gender;
        this.DOB = DOB;
        this.IDNumber = IDNumber;
        this.IDType = IDType;
        FaceImage = faceImage;
        this.IDImage = IDImage;
        Fingerprint = fingerprint;
        CreatedBy = createdBy;
        CreateDate = createDate;
        IsApproved = isApproved;
        ApprovedBy = approvedBy;
        ApproveDate = approveDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
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

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public boolean isApproved() {
        return IsApproved;
    }

    public void setApproved(boolean approved) {
        IsApproved = approved;
    }

    public String getApprovedBy() {
        return ApprovedBy;
    }

    public void setApprovedBy(String approvedBy) {
        ApprovedBy = approvedBy;
    }

    public String getApproveDate() {
        return ApproveDate;
    }

    public void setApproveDate(String approveDate) {
        ApproveDate = approveDate;
    }
}
