package workflow.domain;

/**
 * Created by sramalin on 29/05/17.
 */
public class Ticket {

    private  long id;
    private  String name;
    private   String description;
    private String assignedTo;
    private String priority;

    public Ticket() {
    }

    public Ticket(long id, String name, String description, String assignedTo, String priority) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.assignedTo = assignedTo;
        this.priority = priority;
    }

    public Ticket(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Ticket(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public String getPriority() {
        return priority;
    }
}
