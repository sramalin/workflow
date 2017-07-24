package workflow.domain;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import workflow.security.Authorities;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by sramalin on 29/05/17.
 */
@Entity
public class User {


    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long num;
    @Id
    private  String username;
    private  String firstName;
    private  String lastName;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDob(Date dob) {

        this.dob = dob;
    }

    private String email;
    private Date dob;
    private boolean activationStatus;

    public String getPassword() {
        return password;
    }

    private String password;

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "authority",referencedColumnName="name"))
    private Set<Authority> authorities;


    public User() {
    }

    public long getNum() {
        return num;
    }

    public String getUsername() {

        return username;
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

    public void setActivationStatus(boolean activationStatus) {
        this.activationStatus = activationStatus;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword() {

        //String hashedPwd = BCrypt.hashpw(this.activationStatus+"_"+this.getLastName(), BCrypt.gensalt());
        //this.password = hashedPwd;
        StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder();
        String hashedPwd = standardPasswordEncoder.encode("password");
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
