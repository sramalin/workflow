package workflow.domain;

/**
 * Created by sramalin on 29/05/17.
 */
public class User {

    private final long num;
    private final String userId;
    private final String firstName;
    private final String lastName;

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

    public User(long num, String userId, String firstName, String lastName) {

        this.num = num;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
