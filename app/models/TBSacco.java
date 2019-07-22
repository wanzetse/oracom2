package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbsacco")
public class TBSacco extends Model {

    @Id
    private Integer id;
    private String SaccoCode;
    private String SaccoName;
    private String BrandName;
    private String Location;
    private String Mobile;
    private String Email;
    private String ContactPerson;
    private String CreateDate;
    private String Remarks;
    private String CreatedBy;
    private boolean CreateApproved;
    private String CreateApprovedDate;
    private String CreateApprovedBy;
    private String Updated;
    private String UpdatedBy;
    private String UpdateApproved;
    private String UpdateApprovedDate;
    private String Deleted;
    private String DeletedBy;
    private String DeleteDate;

    public static Finder<Integer,TBSacco> finder=new Finder<>(TBSacco.class);

    public TBSacco(Integer id, String saccoCode, String saccoName, String brandName, String location, String mobile, String email, String contactPerson, String createDate, String remarks, String createdBy, boolean createApproved, String createApprovedDate, String createApprovedBy, String updated, String updatedBy, String updateApproved, String updateApprovedDate, String deleted, String deletedBy, String deleteDate) {
        this.id = id;
        SaccoCode = saccoCode;
        SaccoName = saccoName;
        BrandName = brandName;
        Location = location;
        Mobile = mobile;
        Email = email;
        ContactPerson = contactPerson;
        CreateDate = createDate;
        Remarks = remarks;
        CreatedBy = createdBy;
        CreateApproved = createApproved;
        CreateApprovedDate = createApprovedDate;
        CreateApprovedBy = createApprovedBy;
        Updated = updated;
        UpdatedBy = updatedBy;
        UpdateApproved = updateApproved;
        UpdateApprovedDate = updateApprovedDate;
        Deleted = deleted;
        DeletedBy = deletedBy;
        DeleteDate = deleteDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSaccoCode() {
        return SaccoCode;
    }

    public void setSaccoCode(String saccoCode) {
        SaccoCode = saccoCode;
    }

    public String getSaccoName() {
        return SaccoName;
    }

    public void setSaccoName(String saccoName) {
        SaccoName = saccoName;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public boolean isCreateApproved() {
        return CreateApproved;
    }

    public void setCreateApproved(boolean createApproved) {
        CreateApproved = createApproved;
    }

    public String getCreateApprovedDate() {
        return CreateApprovedDate;
    }

    public void setCreateApprovedDate(String createApprovedDate) {
        CreateApprovedDate = createApprovedDate;
    }

    public String getCreateApprovedBy() {
        return CreateApprovedBy;
    }

    public void setCreateApprovedBy(String createApprovedBy) {
        CreateApprovedBy = createApprovedBy;
    }

    public String getUpdated() {
        return Updated;
    }

    public void setUpdated(String updated) {
        Updated = updated;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }

    public String getUpdateApproved() {
        return UpdateApproved;
    }

    public void setUpdateApproved(String updateApproved) {
        UpdateApproved = updateApproved;
    }

    public String getUpdateApprovedDate() {
        return UpdateApprovedDate;
    }

    public void setUpdateApprovedDate(String updateApprovedDate) {
        UpdateApprovedDate = updateApprovedDate;
    }

    public String getDeleted() {
        return Deleted;
    }

    public void setDeleted(String deleted) {
        Deleted = deleted;
    }

    public String getDeletedBy() {
        return DeletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        DeletedBy = deletedBy;
    }

    public String getDeleteDate() {
        return DeleteDate;
    }

    public void setDeleteDate(String deleteDate) {
        DeleteDate = deleteDate;
    }
}
