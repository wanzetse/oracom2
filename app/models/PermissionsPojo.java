package models;

import be.objectify.deadbolt.java.models.Permission;

public class PermissionsPojo implements Permission{

    private String permissions;

    public PermissionsPojo(String permissions) {
        this.permissions = permissions;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    @Override
    public String getValue() {
        return permissions;
    }
}
