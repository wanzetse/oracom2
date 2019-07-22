package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbcharges")
public class Charges extends Model {

    @Id
    private Integer id;
    private String BranchCode;
    private String TransactionCode;
    private String FromAmount;
    private String ToAmount;
    private boolean IsTransactionFee;
    private String TransactionFee;
    private String CommissionFee;
    private String TransGLCode;
    private boolean IsCommissionFee;
    private String CommissionGLCode;
    private String Channel;
    private String CreateDate;
    private String CreatedBy;
    private boolean Active;
    private boolean Approved;
    private String ApprovedDate;
    private String ApprovedBy;

    public static Finder<Integer, Charges> finder=new Finder<>(Charges.class);

    public Charges(String branchCode, String transactionCode, String fromAmount, String toAmount, boolean isTransactionFee, String transactionFee, String commissionFee, String transGLCode, boolean isCommissionFee, String commissionGLCode, String channel, String createDate, String createdBy, boolean active, boolean approved, String approvedDate, String approvedBy) {
        BranchCode = branchCode;
        TransactionCode = transactionCode;
        FromAmount = fromAmount;
        ToAmount = toAmount;
        IsTransactionFee = isTransactionFee;
        TransactionFee = transactionFee;
        CommissionFee = commissionFee;
        TransGLCode = transGLCode;
        IsCommissionFee = isCommissionFee;
        CommissionGLCode = commissionGLCode;
        Channel = channel;
        CreateDate = createDate;
        CreatedBy = createdBy;
        Active = active;
        Approved = approved;
        ApprovedDate = approvedDate;
        ApprovedBy = approvedBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBranchCode() {
        return BranchCode;
    }

    public void setBranchCode(String branchCode) {
        BranchCode = branchCode;
    }

    public String getTransactionCode() {
        return TransactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        TransactionCode = transactionCode;
    }

    public String getFromAmount() {
        return FromAmount;
    }

    public void setFromAmount(String fromAmount) {
        FromAmount = fromAmount;
    }

    public String getToAmount() {
        return ToAmount;
    }

    public void setToAmount(String toAmount) {
        ToAmount = toAmount;
    }

    public boolean isTransactionFee() {
        return IsTransactionFee;
    }

    public void setTransactionFee(boolean transactionFee) {
        IsTransactionFee = transactionFee;
    }

    public String getTransactionFee() {
        return TransactionFee;
    }

    public void setTransactionFee(String transactionFee) {
        TransactionFee = transactionFee;
    }

    public String getCommissionFee() {
        return CommissionFee;
    }

    public void setCommissionFee(String commissionFee) {
        CommissionFee = commissionFee;
    }

    public String getTransGLCode() {
        return TransGLCode;
    }

    public void setTransGLCode(String transGLCode) {
        TransGLCode = transGLCode;
    }

    public boolean isCommissionFee() {
        return IsCommissionFee;
    }

    public void setCommissionFee(boolean commissionFee) {
        IsCommissionFee = commissionFee;
    }

    public String getCommissionGLCode() {
        return CommissionGLCode;
    }

    public void setCommissionGLCode(String commissionGLCode) {
        CommissionGLCode = commissionGLCode;
    }

    public String getChannel() {
        return Channel;
    }

    public void setChannel(String channel) {
        Channel = channel;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public boolean isApproved() {
        return Approved;
    }

    public void setApproved(boolean approved) {
        Approved = approved;
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
}
