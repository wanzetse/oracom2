package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tbbusinesses")
public class Branch extends Model {


    @Id
    public Integer Id;
    public String Company_Name;
    public String Company_Category;
    public String Company_Subcategory;
    public String Email_1;
    public String Email_2;
    public String Phone_1;
    public String Phone_2;
    public String Website;
    public String County;
    public String Town;
    public String Street_Name;
    public String Building;
    public String MapLatitude;
    public String MapLongitude;
    public String Company_Branch;
    public String Services;
    public String Status;
    public String Comments;
    public String CreatedBy;
    public String dateCreated;
    public boolean selected;




    public Branch(String company_Name, String company_Category, String company_Subcategory, String email_1, String email_2, String phone_1, String phone_2, String website, String county,
                  String town, String Street_Name, String building, String mapLatitude, String MapLongitude, String company_branch, String Status, String services, String comments,
                  String createdBy, String dateCreated) {
        Company_Name = company_Name;
        Company_Category = company_Category;
        Company_Subcategory = company_Subcategory;
        Email_1 = email_1;
        Email_2 = email_2;
        Phone_1 = phone_1;
        Phone_2 = phone_2;
        Website = website;
        County = county;
        Town = town;
        this.Street_Name = Street_Name;
        Building = building;
        MapLatitude = mapLatitude;
        this.MapLongitude = MapLongitude;
        Company_Branch = company_branch;
        this.Status = Status;
        Services = services;
        Comments = comments;
        CreatedBy = createdBy;
        this.dateCreated = dateCreated;
    }

    public Branch() {

    }

   /* @ManyToMany(mappedBy = "branches")
    private List<UserManagement> users;
*/
    public static Finder<Integer, Branch> finder = new Finder<>(Branch.class);


    public String getCeatedBy() {
        return ceatedBy;
    }

    public void setCeatedBy(String ceatedBy) {
        this.ceatedBy = ceatedBy;
    }

    public String ceatedBy;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getStreet_Name() {
        return Street_Name;
    }

    public void setStreet_Name(String street_Name) {
        Street_Name = street_Name;
    }

    public String getMapLongitude() {
        return MapLongitude;
    }

    public void setMapLongitude(String mapLongitude) {
        MapLongitude = mapLongitude;
    }

    public String getCompany_Category() {
        return Company_Category;
    }

    public void setCompany_Category(String company_Category) {
        Company_Category = company_Category;
    }

    public String getCompany_Subcategory() {
        return Company_Subcategory;
    }

    public void setCompany_Subcategory(String company_Subcategory) {
        Company_Subcategory = company_Subcategory;
    }

    public String getEmail_2() {
        return Email_2;
    }

    public void setEmail_2(String email_2) {
        Email_2 = email_2;
    }

    public String getPhone_2() {
        return Phone_2;
    }

    public void setPhone_2(String phone_2) {
        Phone_2 = phone_2;
    }

    public String getCompany_Name() {
        return Company_Name;
    }

    public void setCompany_Name(String company_Name) {
        Company_Name = company_Name;
    }

    public String getTown() {
        return Town;
    }

    public void setTown(String town) {
        Town = town;
    }

    public String getCounty() {
        return County;
    }

    public void setCounty(String county) {
        County = county;
    }

    public String getCompany_Branch() {
        return Company_Branch;
    }

    public void setCompany_Branch(String company_Branch) {
        Company_Branch = company_Branch;
    }

    public String getPhone_1() {
        return Phone_1;
    }

    public void setPhone_1(String phone_1) {
        Phone_1 = phone_1;
    }


    public String getEmail_1() {
        return Email_1;
    }

    public void setEmail_1(String email_1) {
        Email_1 = email_1;
    }


    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getMapLatitude() {
        return MapLatitude;
    }

    public void setMapLatitude(String mapLatitude) {
        this.MapLatitude = mapLatitude;
    }


    public String getServices() {
        return Services;
    }

    public void setServices(String services) {
        Services = services;
    }

    public String getBuilding() {
        return Building;
    }

    public void setBuilding(String building) {
        Building = building;
    }

    public void setBranch(String branch) {

        Company_Branch = branch;

    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

/*
    public List<UserManagement> getUsers() {
        return users;
    }

    public void setUsers(List<UserManagement> users) {
        this.users = users;
    }

    public void addUser(UserManagement userManagement) {
        users.add(userManagement);
    }
*/
    public static Finder<Integer,Branch> find=new Finder<>(Branch.class);
    public static Branch getBranchById(Integer id) {
        return finder.query().where().eq("Id", id).findOne();
    }
}
