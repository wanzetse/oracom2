package models;

import be.objectify.deadbolt.java.models.Permission;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbuserpermissions")
public class tbUserPermissions extends Model implements Permission {

    @Id
    private Integer id;

    @Column(name = "permission_value")
    private String permission;

    public static Finder<Integer,tbUserPermissions> finder=new Finder<>(tbUserPermissions.class);

    public tbUserPermissions(String permission) {
        this.permission = permission;
    }

    @Override
    public String getValue() {
        return null;
    }
    public static tbUserPermissions findByValue(String value)
    {
        return finder.query().where()
                .eq("value",
                        value)
                .findOne();
    }
}
