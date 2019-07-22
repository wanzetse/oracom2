package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="tbmessage")
public class Message extends Model {

    public String subject;
    public String message;

    public Message() {
    }

    public Message(String subject, String message) {

        this.subject = subject;
        this.message = message;

    }
}
