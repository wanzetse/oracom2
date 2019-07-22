package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbloans")
public class TBLoans extends Model {

    @Id
    private Integer Id;
    private String LoanAccount;
    private String LoanAmount;
    private String ApplicationDate;
    private String LastPaymentDate;
    private String DueDate;
    private String LastPaymentAmount;
    private String Balance;
    private String InterestBalance;
    private String CustomerPhoner;
    private String LoanStatus;
    private boolean IsActive;
    private String LoanDisbursed;
    private String Interest;
    private boolean Disbursed;
    private String DisburseDate;
    private String AccountNumber;
    private boolean IsReversed;
    private String ReverseDate;
    private String ReverseBy;

    public static Finder<Integer, TBLoans> finder=new Finder<>(TBLoans.class);

    public TBLoans(String loanAccount, String loanAmount, String applicationDate, String lastPaymentDate, String dueDate, String lastPaymentAmount, String balance, String interestBalance, String customerPhoner, String loanStatus, boolean isActive, String loanDisbursed, String interest, boolean disbursed, String disburseDate, String accountNumber, boolean isReversed, String reverseDate, String reverseBy) {
        LoanAccount = loanAccount;
        LoanAmount = loanAmount;
        ApplicationDate = applicationDate;
        LastPaymentDate = lastPaymentDate;
        DueDate = dueDate;
        LastPaymentAmount = lastPaymentAmount;
        Balance = balance;
        InterestBalance = interestBalance;
        CustomerPhoner = customerPhoner;
        LoanStatus = loanStatus;
        IsActive = isActive;
        LoanDisbursed = loanDisbursed;
        Interest = interest;
        Disbursed = disbursed;
        DisburseDate = disburseDate;
        AccountNumber = accountNumber;
        IsReversed = isReversed;
        ReverseDate = reverseDate;
        ReverseBy = reverseBy;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getLoanAccount() {
        return LoanAccount;
    }

    public void setLoanAccount(String loanAccount) {
        LoanAccount = loanAccount;
    }

    public String getLoanAmount() {
        return LoanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        LoanAmount = loanAmount;
    }

    public String getApplicationDate() {
        return ApplicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        ApplicationDate = applicationDate;
    }

    public String getLastPaymentDate() {
        return LastPaymentDate;
    }

    public void setLastPaymentDate(String lastPaymentDate) {
        LastPaymentDate = lastPaymentDate;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getLastPaymentAmount() {
        return LastPaymentAmount;
    }

    public void setLastPaymentAmount(String lastPaymentAmount) {
        LastPaymentAmount = lastPaymentAmount;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getInterestBalance() {
        return InterestBalance;
    }

    public void setInterestBalance(String interestBalance) {
        InterestBalance = interestBalance;
    }

    public String getCustomerPhoner() {
        return CustomerPhoner;
    }

    public void setCustomerPhoner(String customerPhoner) {
        CustomerPhoner = customerPhoner;
    }

    public String getLoanStatus() {
        return LoanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        LoanStatus = loanStatus;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public String getLoanDisbursed() {
        return LoanDisbursed;
    }

    public void setLoanDisbursed(String loanDisbursed) {
        LoanDisbursed = loanDisbursed;
    }

    public String getInterest() {
        return Interest;
    }

    public void setInterest(String interest) {
        Interest = interest;
    }

    public boolean isDisbursed() {
        return Disbursed;
    }

    public void setDisbursed(boolean disbursed) {
        Disbursed = disbursed;
    }

    public String getDisburseDate() {
        return DisburseDate;
    }

    public void setDisburseDate(String disburseDate) {
        DisburseDate = disburseDate;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public boolean isReversed() {
        return IsReversed;
    }

    public void setReversed(boolean reversed) {
        IsReversed = reversed;
    }

    public String getReverseDate() {
        return ReverseDate;
    }

    public void setReverseDate(String reverseDate) {
        ReverseDate = reverseDate;
    }

    public String getReverseBy() {
        return ReverseBy;
    }

    public void setReverseBy(String reverseBy) {
        ReverseBy = reverseBy;
    }
}
