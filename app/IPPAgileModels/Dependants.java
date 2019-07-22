package IPPAgileModels;

import com.avaje.ebean.Model;
import io.ebean.Finder;
import javax.persistence.Id;

public class Dependants extends Model {

    @Id
    private Integer Id;
    private String FirstName;
    private String MiddleName;
    private String LastName;
    private String Relationship;
    private Integer percentageShare;
    private String Address;
    private String gender;

    public Dependants(Integer id, String firstName, String middleName, String lastName, String relationship,
                      Integer percentageShare, String address, String gender) {
        Id = id;
        FirstName = firstName;
        MiddleName = middleName;
        LastName = lastName;
        Relationship = relationship;
        this.percentageShare = percentageShare;
        Address = address;
        this.gender = gender;
    }

    public static Finder<Integer, Dependants> finder = new Finder<>(Dependants.class);


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getRelationship() {
        return Relationship;
    }

    public void setRelationship(String relationship) {
        Relationship = relationship;
    }

    public Integer getPercentageShare() {
        return percentageShare;
    }

    public void setPercentageShare(Integer percentageShare) {
        this.percentageShare = percentageShare;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
