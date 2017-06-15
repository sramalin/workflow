package workflow.domain;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by sramalin on 29/05/17.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long num;
    private  String userId;
    private  String firstName;
    private  String lastName;

    public String getEmail() {
        return email;
    }

    private String email;
    private Date dob;
    private boolean activationStatus;
    private String password;


    public User() {
    }

    public long getNum() {
        return num;
    }

    public String getUserId() {

        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDOB() {
        return dob;
    }

    public boolean getActivationStatus() {
        return activationStatus;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword() {

        String hashedPwd = BCrypt.hashpw(this.activationStatus+"_"+this.getLastName(), BCrypt.gensalt());
        this.password = hashedPwd;
    }

    public User replace(User user){


        firstName = getNotNullParam(user.getFirstName(), firstName);
        lastName = getNotNullParam(user.getLastName(), lastName);
        dob = getNotNullParam(user.getDOB(),dob);
        activationStatus = getNotNullParam(user.getActivationStatus(),activationStatus);
        return this;
    }

    private <T> T getNotNullParam(T first, T second) {
        if(first != null) {
            return first;
        }
        return second;

    }

}
