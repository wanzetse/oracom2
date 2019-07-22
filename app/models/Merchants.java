package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbmerchants")
public class Merchants extends Model {
    @Id
    private Integer Id;
    private String FirstName;
    private String MidName;
    private String LastName;
    private String PhoneNumber;
    private String Email_Address;
    private String PayBillNumber;
    private String SettlementAc;
    private String Branch;
    private String CreateDate;
    private String CreatedBy;
    private boolean IsApproved;
    private String ApprovedDate;
    private String ApprovedBy;
    private String Username;
    private String password;
    private boolean IsEnabled;
    private String EnabledBy;
    private String EnabledDate;
    private boolean IsDeleted;
    private String DeleteDate;
    private String DeleteBy;
    private boolean IsDisabled;
    private String  DisabledDate;
    private String  DisabledBy;
    private boolean IsFirstLogin;
    private String LoginTries;

    public static Finder<Integer,Merchants> finder=new Finder<>(Merchants.class);

    public Merchants(String createDate,String Branch,String CreatedBy,String payBillNumber,Integer id, String firstName, String midName, String lastName, String phoneNumber, String email_Address, String settlementAc, boolean isApproved, String approvedDate, String approvedBy, String username, String password, boolean isEnabled, String enabledBy, String enabledDate, boolean isDeleted, String deleteDate, String deleteBy, boolean isDisabled, String disabledDate, String disabledBy, boolean isFirstLogin, String loginTries) {
        this.Id = id;
        this.CreateDate = createDate;
        this.FirstName = firstName;
        this.Branch = Branch;
        this.CreatedBy = CreatedBy;
        this.PayBillNumber = payBillNumber;
        this.MidName = midName;
        this.LastName = lastName;
        this.PhoneNumber = phoneNumber;
        this.Email_Address = email_Address;
        this.SettlementAc = settlementAc;
        this.IsApproved = isApproved;
        this.ApprovedDate = approvedDate;
        this.ApprovedBy = approvedBy;
        this.Username = username;
        this.password = password;
        this.IsEnabled = isEnabled;
        this.EnabledBy = enabledBy;
        this.EnabledDate = enabledDate;
        this.IsDeleted = isDeleted;
        this.DeleteDate = deleteDate;
        this.DeleteBy = deleteBy;
        this.IsDisabled = isDisabled;
        this.DisabledDate = disabledDate;
        this.DisabledBy = disabledBy;
        this.IsFirstLogin = isFirstLogin;
        this.LoginTries = loginTries;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMidName() {
        return MidName;
    }

    public void setMidName(String midName) {
        MidName = midName;
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

    public String getEmail_Address() {
        return Email_Address;
    }

    public void setEmail_Address(String email_Address) {
        Email_Address = email_Address;
    }

    public String getSettlementAc() {
        return SettlementAc;
    }

    public void setSettlementAc(String settlementAc) {
        SettlementAc = settlementAc;
    }

    public boolean isApproved() {
        return IsApproved;
    }

    public void setApproved(boolean approved) {
        IsApproved = approved;
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

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return IsEnabled;
    }

    public void setEnabled(boolean enabled) {
        IsEnabled = enabled;
    }

    public String getEnabledBy() {
        return EnabledBy;
    }

    public void setEnabledBy(String enabledBy) {
        EnabledBy = enabledBy;
    }

    public String getEnabledDate() {
        return EnabledDate;
    }

    public void setEnabledDate(String enabledDate) {
        EnabledDate = enabledDate;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    public String getDeleteDate() {
        return DeleteDate;
    }

    public void setDeleteDate(String deleteDate) {
        DeleteDate = deleteDate;
    }

    public String getDeleteBy() {
        return DeleteBy;
    }

    public void setDeleteBy(String deleteBy) {
        DeleteBy = deleteBy;
    }

    public boolean isDisabled() {
        return IsDisabled;
    }

    public void setDisabled(boolean disabled) {
        IsDisabled = disabled;
    }

    public String getDisabledDate() {
        return DisabledDate;
    }

    public void setDisabledDate(String disabledDate) {
        DisabledDate = disabledDate;
    }

    public String getDisabledBy() {
        return DisabledBy;
    }

    public void setDisabledBy(String disabledBy) {
        DisabledBy = disabledBy;
    }

    public boolean isFirstLogin() {
        return IsFirstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        IsFirstLogin = firstLogin;
    }

    public String getLoginTries() {
        return LoginTries;
    }

    public void setLoginTries(String loginTries) {
        LoginTries = loginTries;
    }

    public Integer getId() {
        return Id;
    }

    public String getPayBillNumber() {
        return PayBillNumber;
    }

    public void setPayBillNumber(String payBillNumber) {
        PayBillNumber = payBillNumber;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
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
}

