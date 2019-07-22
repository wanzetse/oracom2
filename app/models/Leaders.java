package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbleaders")
public class Leaders extends Model {

    @Id
    public Integer Id;
    public String Full_Names;
    public String Position;
    public String Status;
    public String leaderCounty;
    public String leaderConstituency;
    public String leaderWard;
    public String leaderComments;
    public String leaderCreatedBy;
    public String leaderDateCreated;
    public boolean selectedLeader;

    public Leaders(String Full_Names,  String Position, String Status, String leaderCounty, String leaderConstituency, String leaderWard, String leaderComments,
                   String leaderCreatedBy, String leaderDateCreated){
        this.Full_Names = Full_Names;
        this.Position = Position;
        this.Status = Status;
        this.leaderCounty = leaderCounty;
        this.leaderConstituency = leaderConstituency;
        this.leaderWard = leaderWard;
        this.leaderComments = leaderComments;
        this.leaderCreatedBy = leaderCreatedBy;
        this.leaderDateCreated = leaderDateCreated;


    }

    public static Finder<Integer, Leaders> finder = new Finder<>(Leaders.class);

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getFull_Names() {
        return Full_Names;
    }

    public void setFull_Names(String full_Names) {
        this.Full_Names = full_Names;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getLeaderCounty() {
        return leaderCounty;
    }

    public void setLeaderCounty(String leaderCounty) {
        this.leaderCounty = leaderCounty;
    }

    public String getLeaderConstituency() {
        return leaderConstituency;
    }

    public void setLeaderConstituency(String leaderConstituency) {
        this.leaderConstituency = leaderConstituency;
    }

    public String getLeaderWard() {
        return leaderWard;
    }

    public void setLeaderWard(String leaderWard) {
        this.leaderWard = leaderWard;
    }

    public String getLeaderComments() {
        return leaderComments;
    }

    public void setLeaderComments(String leaderComments) {
        this.leaderComments = leaderComments;
    }

    public String getLeaderCreatedBy() {
        return leaderCreatedBy;
    }

    public void setLeaderCreatedBy(String leaderCreatedBy) {
        this.leaderCreatedBy = leaderCreatedBy;
    }

    public String getLeaderDateCreated() {
        return leaderDateCreated;
    }

    public void setLeaderDateCreated(String leaderDateCreated) {
        this.leaderDateCreated = leaderDateCreated;
    }

    public boolean isSelectedLeader() {
        return selectedLeader;
    }

    public void setSelectedLeader(boolean selectedLeader) {
        this.selectedLeader = selectedLeader;
    }



}
