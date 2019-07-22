package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="tbprofiles")
public class Profiles extends Model{

    @Id
    public Integer Id;
    public String ProfileName;
    public String Remarks;
    public String CreatedBy;
    public String DateCreated;
    public boolean Approved;
    public boolean  edit;
    public String editDate;
    public boolean  editApproved;
    public boolean  deleted;
    public String  deleteDate;
    public boolean  deleteApproved;

    public static Finder<Integer,Profiles> finder=new Finder<>(Profiles.class);

    public Profiles(String profileName, String remarks, String createdBy, boolean approved) {
        ProfileName = profileName;
        Remarks = remarks;
        CreatedBy = createdBy;
        Approved = approved;
    }

    public String getProfileName() {
        return ProfileName;
    }

    public void setProfileName(String profileName) {
        ProfileName = profileName;
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

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }

    public boolean isApproved() {
        return Approved;
    }

    public void setApproved(boolean approved) {
        Approved = approved;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public String getEditDate() {
        return editDate;
    }

    public void setEditDate(String editDate) {
        this.editDate = editDate;
    }

    public boolean isEditApproved() {
        return editApproved;
    }

    public void setEditApproved(boolean editApproved) {
        this.editApproved = editApproved;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(String deleteDate) {
        this.deleteDate = deleteDate;
    }

    public boolean isDeleteApproved() {
        return deleteApproved;
    }

    public void setDeleteApproved(boolean deleteApproved) {
        this.deleteApproved = deleteApproved;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
