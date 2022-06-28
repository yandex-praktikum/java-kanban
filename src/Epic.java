import java.util.ArrayList;

public class Epic extends Task {
    ArrayList<Integer> subtasksOfEpic;
    public Epic(String name, String description) {
        super(name, description);
        setStatus(Status.NEW);
        subtasksOfEpic = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + getStatus() +
                ", id=" + id +
                '}';
    }
}
