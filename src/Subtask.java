public class Subtask extends Task {
    int epicId;
    Manager manager;



    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
        setStatus(Status.NEW);
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + getStatus() +
                ", id=" + id +
                ", epicId=" + epicId +
                '}';
    }
}
