package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbtranscodes")
public class TransactionCodes extends Model {

    @Id
    private Integer id;
    private String trancode;
    private String transName;
    private boolean IsApproved;
    private String ApproveDate;
    private String ApproveBy;
    private boolean IsDeleted;
    private String deleteDate;
    private String deleteBy;

    public static Finder<Integer,TransactionCodes> finder=new Finder<>(TransactionCodes.class);

    public TransactionCodes(String trancode, String transName, boolean isApproved, String approveDate, String approveBy, boolean isDeleted, String deleteDate, String deleteBy) {
        this.trancode = trancode;
        this.transName = transName;
        IsApproved = isApproved;
        ApproveDate = approveDate;
        ApproveBy = approveBy;
        IsDeleted = isDeleted;
        this.deleteDate = deleteDate;
        this.deleteBy = deleteBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTrancode() {
        return trancode;
    }

    public void setTrancode(String trancode) {
        this.trancode = trancode;
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
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

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    public String getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(String deleteDate) {
        this.deleteDate = deleteDate;
    }

    public String getDeleteBy() {
        return deleteBy;
    }

    public void setDeleteBy(String deleteBy) {
        this.deleteBy = deleteBy;
    }
}
