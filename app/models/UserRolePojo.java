package models;

import be.objectify.deadbolt.java.models.Role;

public class UserRolePojo implements Role {

    private String RoleName;

    public UserRolePojo(String RoleName) {
        this.RoleName = RoleName;
    }


    public String getRolename() {
        return RoleName;
    }

    public void setRolename(String RoleName) {
        this.RoleName = RoleName;
    }

    @Override
    public String getName() {
        return RoleName;
    }
}
