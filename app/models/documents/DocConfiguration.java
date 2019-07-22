package models.documents;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_docs")
public class DocConfiguration extends Model {
    @Id
    private Integer id;
    private String BusinessType;
    private boolean ApplnForm;
    private boolean DirectorID;
    private boolean KraPin;
    private boolean CouncilPermit;
    private boolean BusinessRegCert;
    private boolean UtilityBill;
    private boolean BusinessPhoto;
    private boolean BankLetter;
    private boolean CancelledCheque;
    private boolean BoardResolution;
    private boolean CR12;
    private boolean RegulatoryAuthorityLetter;
    private boolean LastAuditedAccounts;
    private String CreatedBy;
    private String CreateDate;
    private String EditedBy;
    private String EditDate;

    public static Finder<Integer,DocConfiguration> finder=new Finder<>(DocConfiguration.class);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessType() {
        return BusinessType;
    }

    public void setBusinessType(String businessType) {
        BusinessType = businessType;
    }

    public boolean isApplnForm() {
        return ApplnForm;
    }

    public void setApplnForm(boolean applnForm) {
        ApplnForm = applnForm;
    }

    public boolean isDirectorID() {
        return DirectorID;
    }

    public void setDirectorID(boolean directorID) {
        DirectorID = directorID;
    }

    public boolean isKraPin() {
        return KraPin;
    }

    public void setKraPin(boolean kraPin) {
        KraPin = kraPin;
    }

    public boolean isCouncilPermit() {
        return CouncilPermit;
    }

    public void setCouncilPermit(boolean councilPermit) {
        CouncilPermit = councilPermit;
    }

    public boolean isBusinessRegCert() {
        return BusinessRegCert;
    }

    public void setBusinessRegCert(boolean businessRegCert) {
        BusinessRegCert = businessRegCert;
    }

    public boolean isUtilityBill() {
        return UtilityBill;
    }

    public void setUtilityBill(boolean utilityBill) {
        UtilityBill = utilityBill;
    }

    public boolean isBusinessPhoto() {
        return BusinessPhoto;
    }

    public void setBusinessPhoto(boolean businessPhoto) {
        BusinessPhoto = businessPhoto;
    }

    public boolean isBankLetter() {
        return BankLetter;
    }

    public void setBankLetter(boolean bankLetter) {
        BankLetter = bankLetter;
    }

    public boolean isCancelledCheque() {
        return CancelledCheque;
    }

    public void setCancelledCheque(boolean cancelledCheque) {
        CancelledCheque = cancelledCheque;
    }

    public boolean isBoardResolution() {
        return BoardResolution;
    }

    public void setBoardResolution(boolean boardResolution) {
        BoardResolution = boardResolution;
    }

    public boolean isCR12() {
        return CR12;
    }

    public void setCR12(boolean CR12) {
        this.CR12 = CR12;
    }

    public boolean isRegulatoryAuthorityLetter() {
        return RegulatoryAuthorityLetter;
    }

    public void setRegulatoryAuthorityLetter(boolean regulatoryAuthorityLetter) {
        RegulatoryAuthorityLetter = regulatoryAuthorityLetter;
    }

    public boolean isLastAuditedAccounts() {
        return LastAuditedAccounts;
    }

    public void setLastAuditedAccounts(boolean lastAuditedAccounts) {
        LastAuditedAccounts = lastAuditedAccounts;
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

    public String getEditedBy() {
        return EditedBy;
    }

    public void setEditedBy(String editedBy) {
        EditedBy = editedBy;
    }

    public String getEditDate() {
        return EditDate;
    }

    public void setEditDate(String editDate) {
        EditDate = editDate;
    }

}
