package models.tillManagement;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class t_Tills extends Model {
    @Id
    public Integer id;
    private String orderNumber;
    private String tillType;
    private String storeNumber;
    private String TillNumber;
    private String createdby;
    private String orderDate;
    private boolean IsApproved;
    private String approvedDate;
    private String approvedBy;
    private boolean IsDeleted;
    private String deletedBy;
    private boolean IsDeleteApproved;
    private String deleteApprovedBy;
    private String deleteApprovedDate;


    public static Finder<Integer, t_Tills> finder = new Finder<>(t_Tills.class);

   // @ManyToOne(optional = false)
  //  private Business order_owner;

    public t_Tills(String orderNumber, String tillType, String storeNumber, String tillNumber, String createdby, String orderDate) {
        this.orderNumber = orderNumber;
        this.tillType = tillType;
        this.storeNumber = storeNumber;
        TillNumber = tillNumber;
        this.createdby = createdby;
        this.orderDate = orderDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getTillType() {
        return tillType;
    }

    public void setTillType(String tillType) {
        this.tillType = tillType;
    }

    public String getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public String getTillNumber() {
        return TillNumber;
    }

    public void setTillNumber(String tillNumber) {
        TillNumber = tillNumber;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public boolean isApproved() {
        return IsApproved;
    }

    public void setApproved(boolean approved) {
        IsApproved = approved;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public boolean isDeleteApproved() {
        return IsDeleteApproved;
    }

    public void setDeleteApproved(boolean deleteApproved) {
        IsDeleteApproved = deleteApproved;
    }

    public String getDeleteApprovedBy() {
        return deleteApprovedBy;
    }

    public void setDeleteApprovedBy(String deleteApprovedBy) {
        this.deleteApprovedBy = deleteApprovedBy;
    }

    public String getDeleteApprovedDate() {
        return deleteApprovedDate;
    }

    public void setDeleteApprovedDate(String deleteApprovedDate) {
        this.deleteApprovedDate = deleteApprovedDate;
    }
/*
    public Business getOrder_owner() {
        return order_owner;
    }

    public void setOrder_owner(Business order_owner) {
        this.order_owner = order_owner;
    }
    */
}
