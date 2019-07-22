package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tbusersmanagement")
public class UsersManagement extends Model {
    @Id
    private Integer id;
    private String first_name;
    private String mid_name;
    private String last_name;
    private String mobile_number;
    private String email;
    private String user_name;
    private String password;
    private String IdNumber;
    private String createdBy;
    private String DateCreated;
    private String remarks;
    private boolean ResetPass;
    private String PassResetBy;
    private Date PassResetDate;
    private boolean disabled;
    private String DisabledBy;
    private Date DisabledDate;



    public static Finder<Integer, UsersManagement> finder = new Finder<>(UsersManagement.class);

    public UsersManagement(String first_name, String mid_name, String last_name, String mobile_number, String email, String user_name, String password, String login_tries, String idNumber) {
        this.first_name = first_name;
        this.mid_name = mid_name;
        this.last_name = last_name;
        this.mobile_number = mobile_number;
        this.email = email;
        this.user_name = user_name;
        this.password = password;
        IdNumber = idNumber;
    }

    public static UsersManagement getBranchById(Integer id) {
        return finder.query().where().eq("Id", id).findOne();
    }

}
