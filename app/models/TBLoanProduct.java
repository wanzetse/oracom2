package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbloanproduct")
public class TBLoanProduct extends Model {
    @Id
    private Integer id;
    private String Description;
    private String ProductCode;
    private String MaxAmount;
    private String MinAmount;
    private String Interest;
    private String LoanLimit;
    private String RepaymentType;
    private String MaxValue;
    private String MaxPeriod;
    private String PenaltyRate;
    private boolean IsApproved;
    private String approveDate;
    private String approveBy;
    private boolean isDeleted;
    private String deleteDate;
    private String deletedBy;

    public static Finder<Integer, TBLoanProduct> finder=new Finder<>(TBLoanProduct.class);

    public TBLoanProduct(String description, String productCode, String maxAmount, String minAmount, String interest, String loanLimit, String repaymentType, String maxValue, String maxPeriod, String penaltyRate, boolean isApproved, String approveDate, String approveBy, boolean isDeleted, String deleteDate, String deletedBy) {
        Description = description;
        ProductCode = productCode;
        MaxAmount = maxAmount;
        MinAmount = minAmount;
        Interest = interest;
        LoanLimit = loanLimit;
        RepaymentType = repaymentType;
        MaxValue = maxValue;
        MaxPeriod = maxPeriod;
        PenaltyRate = penaltyRate;
        IsApproved = isApproved;
        this.approveDate = approveDate;
        this.approveBy = approveBy;
        this.isDeleted = isDeleted;
        this.deleteDate = deleteDate;
        this.deletedBy = deletedBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getMaxAmount() {
        return MaxAmount;
    }

    public void setMaxAmount(String maxAmount) {
        MaxAmount = maxAmount;
    }

    public String getMinAmount() {
        return MinAmount;
    }

    public void setMinAmount(String minAmount) {
        MinAmount = minAmount;
    }

    public String getInterest() {
        return Interest;
    }

    public void setInterest(String interest) {
        Interest = interest;
    }

    public String getLoanLimit() {
        return LoanLimit;
    }

    public void setLoanLimit(String loanLimit) {
        LoanLimit = loanLimit;
    }

    public String getRepaymentType() {
        return RepaymentType;
    }

    public void setRepaymentType(String repaymentType) {
        RepaymentType = repaymentType;
    }

    public String getMaxValue() {
        return MaxValue;
    }

    public void setMaxValue(String maxValue) {
        MaxValue = maxValue;
    }

    public String getMaxPeriod() {
        return MaxPeriod;
    }

    public void setMaxPeriod(String maxPeriod) {
        MaxPeriod = maxPeriod;
    }

    public String getPenaltyRate() {
        return PenaltyRate;
    }

    public void setPenaltyRate(String penaltyRate) {
        PenaltyRate = penaltyRate;
    }

    public boolean isApproved() {
        return IsApproved;
    }

    public void setApproved(boolean approved) {
        IsApproved = approved;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public String getApproveBy() {
        return approveBy;
    }

    public void setApproveBy(String approveBy) {
        this.approveBy = approveBy;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(String deleteDate) {
        this.deleteDate = deleteDate;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }
}
