
public class Task {
    String name;
    String description;
    int id;
    private Status status;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        setStatus(Status.NEW);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", id=" + id +
                '}';
    }
}
