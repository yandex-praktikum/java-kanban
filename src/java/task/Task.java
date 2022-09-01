package task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task { // класс родитель
    protected String name; // название задачи
    protected Integer id; // идентификационный номер (будет ключём в мапе)
    protected String description; // описание задачи
    protected Status status; // enum статусов
    protected Duration duration; // продолжительность таски
    protected LocalDateTime startTime; // дата старта
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yy");

    public Task(String name, Integer id, String description,
                Status status, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }

    public void setStatus(Status status) {
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

    public Status getStatus() {
        return status;
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        LocalDateTime endTime = LocalDateTime.from(startTime).plus(duration);
        return endTime.format(formatter);
    }

    @Override
    public String toString() {
        return "{Type: Task" +
                ", name: " + name +
                ", id: " + id +
                ", description: " + description +
                ", status: " + status +
                ", duration: " + duration +
                ", startTime: " + startTime.format(formatter) +
                ", endTime: " + getEndTime() +"}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return Objects.equals(name, task.name) && Objects.equals(id, task.id)
                && Objects.equals(description, task.description) && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, description, status);
    }
}
