package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbuserauthorization")
public class tb_UserAuthorization extends Model {

    @Id
    public Integer id;
    public String role;
    @Column(length = 4000)
    public String permissions;

    public static Finder<Integer,tb_UserAuthorization> finder=new Finder<>(tb_UserAuthorization.class);

    public tb_UserAuthorization(String role, String permissions) {
        this.role = role;
        this.permissions = permissions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
