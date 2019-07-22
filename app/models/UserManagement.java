package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tbusers")
public class UserManagement extends Model {

    @Id
    public Integer id;
    public String first_name;
    public String mid_name;
    public String last_name;
    public String department;
    public String mobile_number;
    public String email;
    public String RoleName;
    public String BranchName;
    public String user_name;
    public String password;
    public String login_tries;
    public String createdBy;
    public String DateCreated;
    public String remarks;
    public boolean CreateApproved;
    public String CreateApprovedBy;
    public String CreateApprovedDate;
    public String CreateApprovedRemarks;
    public boolean Rejected;
    public String RejectedBy;
    public String DateRejected;
    public boolean Updated;
    public String UpdatedBy;
    public String DateUpdated;
    public boolean ResetPass;
    public String PassResetBy;
    public Date PassResetDate;
    public boolean disabled;
    public String DisabledBy;
    public Date DisabledDate;
    public boolean Deleted;
    public String DeletedBy;
    public String DeleteDate;
    public Integer BranchId;
    public Integer ProfileId;

   // @ManyToMany(mappedBy = "tbbusinesses")

    public static Finder<Integer, UserManagement> finder = new Finder<>(UserManagement.class);

    public UserManagement(Integer id, String first_name, String mid_name, String last_name, String department,
                          String mobile_number, String email, String profileName, String branchName, String user_name,
                          String password, String login_tries, String createdBy, String dateCreated, String remarks,
                          boolean createApproved, String createApprovedBy, String createApprovedDate, String createApprovedRemarks,
                          boolean rejected, String rejectedBy, String dateRejected, boolean updated, String updatedBy, String dateUpdated,
                          boolean resetPass, String passResetBy, Date passResetDate, boolean disabled, String disabledBy, Date disabledDate,
                          boolean deleted, String deletedBy, Date deleteDate, Integer branchId, Integer profileId) {
        this.id = id;
        this.first_name = first_name;
        this.mid_name = mid_name;
        this.last_name = last_name;
        this.department = department;
        this.mobile_number = mobile_number;
        this.email = email;
        RoleName = profileName;
        BranchName = branchName;
        this.user_name = user_name;
        this.password = password;
        this.login_tries = login_tries;
        this.createdBy = createdBy;
        DateCreated = dateCreated;
        this.remarks = remarks;
        CreateApproved = createApproved;
        CreateApprovedBy = createApprovedBy;
        CreateApprovedDate = createApprovedDate;
        CreateApprovedRemarks = createApprovedRemarks;
        Rejected = rejected;
        RejectedBy = rejectedBy;
        DateRejected = dateRejected;
        Updated = updated;
        UpdatedBy = updatedBy;
        DateUpdated = dateUpdated;
        ResetPass = resetPass;
        PassResetBy = passResetBy;
        PassResetDate = passResetDate;
        this.disabled = disabled;
        DisabledBy = disabledBy;
        DisabledDate = disabledDate;
        Deleted = deleted;
        DeletedBy = deletedBy;
        DeleteDate = String.valueOf(deleteDate);
        BranchId = branchId;
        ProfileId = profileId;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDateCreated() {
        return DateCreated;
    }

    public boolean isCreateApproved() {
        return CreateApproved;
    }

    public void setCreateApproved(boolean createApproved) {
        CreateApproved = createApproved;
    }

    public String getCreateApprovedBy() {
        return CreateApprovedBy;
    }

    public void setCreateApprovedBy(String createApprovedBy) {
        CreateApprovedBy = createApprovedBy;
    }

    public String getCreateApprovedDate() {
        return CreateApprovedDate;
    }

    public void setCreateApprovedDate(String createApprovedDate) {
        CreateApprovedDate = createApprovedDate;
    }

    public boolean isRejected() {
        return Rejected;
    }

    public void setRejected(boolean rejected) {
        Rejected = rejected;
    }

    public String getRejectedBy() {
        return RejectedBy;
    }

    public void setRejectedBy(String rejectedBy) {
        RejectedBy = rejectedBy;
    }

    public String getDateRejected() {
        return DateRejected;
    }

    public void setDateRejected(String dateRejected) {
        DateRejected = dateRejected;
    }

    public boolean isUpdated() {
        return Updated;
    }

    public void setUpdated(boolean updated) {
        Updated = updated;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }

    public String getDateUpdated() {
        return DateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        DateUpdated = dateUpdated;
    }

    public boolean isResetPass() {
        return ResetPass;
    }

    public void setResetPass(boolean resetPass) {
        ResetPass = resetPass;
    }

    public String getPassResetBy() {
        return PassResetBy;
    }

    public void setPassResetBy(String passResetBy) {
        PassResetBy = passResetBy;
    }

    public Date getPassResetDate() {
        return PassResetDate;
    }

    public void setPassResetDate(Date passResetDate) {
        PassResetDate = passResetDate;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getDisabledBy() {
        return DisabledBy;
    }

    public void setDisabledBy(String disabledBy) {
        DisabledBy = disabledBy;
    }

    public Date getDisabledDate() {
        return DisabledDate;
    }

    public void setDisabledDate(Date disabledDate) {
        DisabledDate = disabledDate;
    }

    public boolean isDeleted() {
        return Deleted;
    }

    public void setDeleted(boolean deleted) {
        Deleted = deleted;
    }

    public String getDeletedBy() {
        return DeletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        DeletedBy = deletedBy;
    }

    public String getDeleteDate() {
        return String.valueOf(DeleteDate);
    }

    public void setDeleteDate(String deleteDate) {
        DeleteDate = deleteDate;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBranch() {
        return BranchName;
    }

    public void setBranch(String branch) {
        this.BranchName = branch;
    }

    public String getLogin_tries() {
        return login_tries;
    }

    public void setLogin_tries(String login_tries) {
        this.login_tries = login_tries;
    }

    /*
        public String getProfile_name() {
            return RoleName;
        }

        public void setProfile_name(String profile_name) {
            this.RoleName = profile_name;
        }
    */
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMid_name() {
        return mid_name;
    }

    public void setMid_name(String mid_name) {
        this.mid_name = mid_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getProfileName() {
        return RoleName;
    }

    public void setProfileName(String profileName) {
        RoleName = profileName;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String branchName) {
        BranchName = branchName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreateApprovedRemarks() {
        return CreateApprovedRemarks;
    }

    public void setCreateApprovedRemarks(String createApprovedRemarks) {
        CreateApprovedRemarks = createApprovedRemarks;
    }

    public Integer getBranchId() {
        return BranchId;
    }

    public void setBranchId(Integer branchId) {
        BranchId = branchId;
    }

    public Integer getProfileId() {
        return ProfileId;
    }

    public void setProfileId(Integer profileId) {
        ProfileId = profileId;
    }

}
