package Task;

import java.util.Objects;

public class Task { // класс родитель
    protected String name; // название задачи
    protected Integer id; // идентификационный номер (будет ключём в мапе)
    protected String description; // описание задачи
    protected String status; // статус задачи NEW IN_PROGRESS DONE

    public Task(String name, Integer id, String description, String status) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "TaskManager.Task{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) && Objects.equals(id, task.id)
                && Objects.equals(description, task.description) && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, description, status);
    }
}
