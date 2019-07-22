package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbaccount")
public class TBAccount extends Model {
    @Id
    private Integer id;
    private String FirstName;
    private String MiddleName;
    private String LastName;
    private String IDNumber;
    private String AccountNumber;
    private String ProductCode;
    private String Balance;
    private String CreatedOn;
    private String CreatedBy;
    private boolean IsApproved;
    private String ApproveDate;
    private String ApproveBy;
    private boolean IsDisabled;
    private String DisableDate;
    private String DisabledBy;

    public static Finder<Integer, TBAccount> finder=new Finder<>(TBAccount.class);

    public TBAccount(String firstName, String middleName, String lastName, String IDNumber, String accountNumber, String productCode, String balance, String createdOn, String createdBy, boolean isApproved, String approveDate, String approveBy, boolean isDisabled, String disableDate, String disabledBy) {
        FirstName = firstName;
        MiddleName = middleName;
        LastName = lastName;
        this.IDNumber = IDNumber;
        AccountNumber = accountNumber;
        ProductCode = productCode;
        Balance = balance;
        CreatedOn = createdOn;
        CreatedBy = createdBy;
        IsApproved = isApproved;
        ApproveDate = approveDate;
        ApproveBy = approveBy;
        IsDisabled = isDisabled;
        DisableDate = disableDate;
        DisabledBy = disabledBy;
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

    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public boolean isApproved() {
        return IsApproved;
    }

    public void setApproved(boolean approved) {
        IsApproved = approved;
    }

    public String getApproveDate() {
        return ApproveDate;
    }

    public void setApproveDate(String approveDate) {
        ApproveDate = approveDate;
    }

    public String getApproveBy() {
        return ApproveBy;
    }

    public void setApproveBy(String approveBy) {
        ApproveBy = approveBy;
    }

    public boolean isDisabled() {
        return IsDisabled;
    }

    public void setDisabled(boolean disabled) {
        IsDisabled = disabled;
    }

    public String getDisableDate() {
        return DisableDate;
    }

    public void setDisableDate(String disableDate) {
        DisableDate = disableDate;
    }

    public String getDisabledBy() {
        return DisabledBy;
    }

    public void setDisabledBy(String disabledBy) {
        DisabledBy = disabledBy;
    }
}
