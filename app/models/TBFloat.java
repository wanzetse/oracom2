package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbfloat")
public class TBFloat extends Model {

    @Id
    private Integer id;
    private String BranchId;
    private String AgentAccount;
    private String TransType;
    private String TransDate;
    private boolean Approved;
    private String ApprovedBy;
    private String ApproveDate;

    public static Finder<Integer, TBFloat> finder=new Finder<>(TBFloat.class);

    public TBFloat(String branchId, String agentAccount, String transType, String transDate, boolean approved, String approvedBy, String approveDate) {
        BranchId = branchId;
        AgentAccount = agentAccount;
        TransType = transType;
        TransDate = transDate;
        Approved = approved;
        ApprovedBy = approvedBy;
        ApproveDate = approveDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBranchId() {
        return BranchId;
    }

    public void setBranchId(String branchId) {
        BranchId = branchId;
    }

    public String getAgentAccount() {
        return AgentAccount;
    }

    public void setAgentAccount(String agentAccount) {
        AgentAccount = agentAccount;
    }

    public String getTransType() {
        return TransType;
    }

    public void setTransType(String transType) {
        TransType = transType;
    }

    public String getTransDate() {
        return TransDate;
    }

    public void setTransDate(String transDate) {
        TransDate = transDate;
    }

    public boolean isApproved() {
        return Approved;
    }

    public void setApproved(boolean approved) {
        Approved = approved;
    }

    public String getApprovedBy() {
        return ApprovedBy;
    }

    public void setApprovedBy(String approvedBy) {
        ApprovedBy = approvedBy;
    }

    public String getApproveDate() {
        return ApproveDate;
    }

    public void setApproveDate(String approveDate) {
        ApproveDate = approveDate;
    }
}
