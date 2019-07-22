package models;

import be.objectify.deadbolt.java.models.Role;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbroles")
public class UserRoles extends Model{

    @Id
    private Integer id;
    public String RoleName;
   // private String RoleDescription;
   public String Remarks;
    public String CreatedBy;
    public String DateCreated;
    public boolean Approved;
    public boolean  edit;
    public String editDate;
    public boolean  editApproved;
    public boolean  deleted;
    private String  deleteDate;
    public boolean  deleteApproved;

    public static Finder<Integer,UserRoles> finder=new Finder<>(UserRoles.class);


    public UserRoles(String roleName,  String remarks) {
        RoleName = roleName;
      //  RoleDescription = roleDescription;
        Remarks = remarks;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }
/*
    public String getRoleDescription() {
        return RoleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        RoleDescription = roleDescription;
    }
*/
    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
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

    public boolean isDelete() {
        return deleted;
    }

    public void setDelete(boolean delete) {
        this.deleted = delete;
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

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
