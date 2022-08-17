package task;

import java.util.Objects;

public class SubTask extends Task { //добавил параметр для связи с эпиком
    private final Integer epicId;
    public SubTask(String name, Integer id, String description, Status status, Integer epicId) {
        super(name, id, description, status);
        this.epicId = epicId;
    }

    public Integer getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "{Type: SubTask" +
                ", name: " + name +
                ", id: " + id +
                ", description: " + description +
                ", status: " + status +
                ", epicId: " + epicId + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubTask)) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return Objects.equals(epicId, subTask.epicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }
}
