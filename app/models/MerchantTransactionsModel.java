package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbmerchanttransactions")
public class MerchantTransactionsModel extends Model {

    @Id
    private Integer Id;
    private String FirstName;
    private String MiddleName;
    private String LastName;
    private String SettlementAc;
    private String MSISDN;
    private String TransactionType;
    private String TransID;
    private String TransTime;
    private String TransAmount;
    private String BusinessShortCode;
    private String BillRefNumber;
    private String InvoiceNumber;
    private String OrgAccountBalance;
    private String ThirdPartyTransID;

    public static Finder<Integer, MerchantTransactionsModel> finder=new Finder<>(MerchantTransactionsModel.class);

    public MerchantTransactionsModel(Integer id, String firstName, String middleName, String lastName, String settlementAc, String MSISDN, String transactionType, String transID, String transTime, String transAmount, String businessShortCode, String billRefNumber, String invoiceNumber, String orgAccountBalance, String thirdPartyTransID) {
        Id = id;
        FirstName = firstName;
        MiddleName = middleName;
        LastName = lastName;
        SettlementAc = settlementAc;
        this.MSISDN = MSISDN;
        TransactionType = transactionType;
        TransID = transID;
        TransTime = transTime;
        TransAmount = transAmount;
        BusinessShortCode = businessShortCode;
        BillRefNumber = billRefNumber;
        InvoiceNumber = invoiceNumber;
        OrgAccountBalance = orgAccountBalance;
        ThirdPartyTransID = thirdPartyTransID;
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

    public String getSettlementAc() {
        return SettlementAc;
    }

    public void setSettlementAc(String settlementAc) {
        SettlementAc = settlementAc;
    }

    public String getMSISDN() {
        return MSISDN;
    }

    public void setMSISDN(String MSISDN) {
        this.MSISDN = MSISDN;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(String transactionType) {
        TransactionType = transactionType;
    }

    public String getTransID() {
        return TransID;
    }

    public void setTransID(String transID) {
        TransID = transID;
    }

    public String getTransTime() {
        return TransTime;
    }

    public void setTransTime(String transTime) {
        TransTime = transTime;
    }

    public String getTransAmount() {
        return TransAmount;
    }

    public void setTransAmount(String transAmount) {
        TransAmount = transAmount;
    }

    public String getBusinessShortCode() {
        return BusinessShortCode;
    }

    public void setBusinessShortCode(String businessShortCode) {
        BusinessShortCode = businessShortCode;
    }

    public String getBillRefNumber() {
        return BillRefNumber;
    }

    public void setBillRefNumber(String billRefNumber) {
        BillRefNumber = billRefNumber;
    }

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }

    public String getOrgAccountBalance() {
        return OrgAccountBalance;
    }

    public void setOrgAccountBalance(String orgAccountBalance) {
        OrgAccountBalance = orgAccountBalance;
    }

    public String getThirdPartyTransID() {
        return ThirdPartyTransID;
    }

    public void setThirdPartyTransID(String thirdPartyTransID) {
        ThirdPartyTransID = thirdPartyTransID;
    }
}
