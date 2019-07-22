package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbtransactions")
public class TBTransactions extends Model {

    @Id
    private Integer id;
    private String AccountNumber;
    private String DRCR;
    private String TransactionCode;
    private String Amount;
    private String TrxDate;
    private String FinancialYear;
    private String FinancialPeriod;
    private String Narration;

    public static Finder<Integer, TBTransactions> finder=new Finder<>(TBTransactions.class);

    public TBTransactions(String accountNumber, String DRCR, String transactionCode, String amount, String trxDate, String financialYear, String financialPeriod, String narration) {
        AccountNumber = accountNumber;
        this.DRCR = DRCR;
        TransactionCode = transactionCode;
        Amount = amount;
        TrxDate = trxDate;
        FinancialYear = financialYear;
        FinancialPeriod = financialPeriod;
        Narration = narration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public String getDRCR() {
        return DRCR;
    }

    public void setDRCR(String DRCR) {
        this.DRCR = DRCR;
    }

    public String getTransactionCode() {
        return TransactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        TransactionCode = transactionCode;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getTrxDate() {
        return TrxDate;
    }

    public void setTrxDate(String trxDate) {
        TrxDate = trxDate;
    }

    public String getFinancialYear() {
        return FinancialYear;
    }

    public void setFinancialYear(String financialYear) {
        FinancialYear = financialYear;
    }

    public String getFinancialPeriod() {
        return FinancialPeriod;
    }

    public void setFinancialPeriod(String financialPeriod) {
        FinancialPeriod = financialPeriod;
    }

    public String getNarration() {
        return Narration;
    }

    public void setNarration(String narration) {
        Narration = narration;
    }
}
