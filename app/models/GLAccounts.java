package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbglaccounts")
public class GLAccounts extends Model {

    @Id
    private Integer id;
    private String GlName;
    private String GlCode;
    private boolean IsSacco;
    private String BranchCode;

    public static Finder<Integer, GLAccounts> finder=new Finder<>(GLAccounts.class);

    public GLAccounts(Integer id, String glName, String glCode, boolean isSacco, String branchCode) {
        this.id = id;
        GlName = glName;
        GlCode = glCode;
        IsSacco = isSacco;
        BranchCode = branchCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGlName() {
        return GlName;
    }

    public void setGlName(String glName) {
        GlName = glName;
    }

    public String getGlCode() {
        return GlCode;
    }

    public void setGlCode(String glCode) {
        GlCode = glCode;
    }

    public boolean isSacco() {
        return IsSacco;
    }

    public void setSacco(boolean sacco) {
        IsSacco = sacco;
    }

    public String getBranchCode() {
        return BranchCode;
    }

    public void setBranchCode(String branchCode) {
        BranchCode = branchCode;
    }
}
