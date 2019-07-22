package models.tillManagement;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;

@Entity
public class t_TillsApplication extends Model {

    @Id
    public Integer id;
    private String orderNumber;
    private String tillType;
    private String createdby;
    private String orderDate;
    private boolean IsApproved;
    private boolean isConfirmed;
    private String approvedDate;
    private String approvedBy;
    private boolean IsDeleted;
    private String deletedBy;
    private boolean IsDeleteApproved;
    private String deleteApprovedBy;
    private String deleteApprovedDate;
/*
    @ManyToOne(optional = false)
    private Business order_owner;
*/
    public static Finder<Integer, t_TillsApplication> finder = new Finder<>(t_TillsApplication.class);


    public t_TillsApplication(String orderNumber, String tillType, String createdby, String orderDate, boolean isApproved, boolean isConfirmed) {
        this.orderNumber = orderNumber;
        this.tillType = tillType;
        this.createdby = createdby;
        this.orderDate = orderDate;
        IsApproved = isApproved;
        this.isConfirmed = isConfirmed;
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

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
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
    public Business getOwner() {
        return order_owner;
    }

    public void setOwner(Business owner) {
        this.order_owner = owner;
    }
    */
}
