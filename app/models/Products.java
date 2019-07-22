package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbproducts")
public class Products extends Model {

    @Id
    private Integer id;
    private String productId;
    private String Description;
    private String productClass;
    private String productCode;
    private boolean IsApproved;
    private String ApproveDate;
    private String ApprovedBy;
    private boolean IsDelete;
    private String DeleteDate;
    private String deleteBy;

    public static Finder<Integer,Products> finder=new Finder<>(Products.class);


    public Products(String productId, String description, String productClass, String productCode, boolean isApproved, String approveDate, String approvedBy, boolean isDelete, String deleteDate, String deleteBy) {
        this.productId = productId;
        Description = description;
        this.productClass = productClass;
        this.productCode = productCode;
        IsApproved = isApproved;
        ApproveDate = approveDate;
        ApprovedBy = approvedBy;
        IsDelete = isDelete;
        DeleteDate = deleteDate;
        this.deleteBy = deleteBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getProductClass() {
        return productClass;
    }

    public void setProductClass(String productClass) {
        this.productClass = productClass;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    public String getApprovedBy() {
        return ApprovedBy;
    }

    public void setApprovedBy(String approvedBy) {
        ApprovedBy = approvedBy;
    }

    public boolean isDelete() {
        return IsDelete;
    }

    public void setDelete(boolean delete) {
        IsDelete = delete;
    }

    public String getDeleteDate() {
        return DeleteDate;
    }

    public void setDeleteDate(String deleteDate) {
        DeleteDate = deleteDate;
    }

    public String getDeleteBy() {
        return deleteBy;
    }

    public void setDeleteBy(String deleteBy) {
        this.deleteBy = deleteBy;
    }
}
