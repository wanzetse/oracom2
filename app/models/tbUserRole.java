package models;

import be.objectify.deadbolt.java.models.Role;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbuserrole")
public class tbUserRole extends Model implements Role {

    @Id
    public Integer id;
    public String roleName;

    public static Finder<Integer, tbUserRole> finder = new Finder<>(tbUserRole.class);

    public tbUserRole(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getName() {
        return roleName;
    }

    public static tbUserRole findByName(String name) {
        return finder.query().where()
                .eq("name",
                        name)
                .findOne();
    }
}
