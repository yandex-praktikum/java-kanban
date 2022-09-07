package task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Epic extends Task {
    private List<Integer> subTaskIds = new ArrayList<>();

    LocalDateTime endTime; // время окончания самой поздней сабтаски
    public Epic(String name, Integer id, String description, Long duration,
                LocalDateTime startTime, LocalDateTime endTime) {

        super(name, id, description, Status.NEW, duration, startTime);
        this.endTime = endTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void deleteSubTaskIds() { // очистить список айди сабтасков
       subTaskIds.clear();
    }

    public void addSubTaskIds(int id){ // добавить айди в список при добавлении новой сабтаски
       subTaskIds.add(id);
    }

    public void deleteSubTaskFromList(Integer id){ //
                subTaskIds.remove(id);
    }

    public int getSubTaskId(int id) {
        return subTaskIds.get(id);
    }

    public List<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void setSubTaskIds(List<Integer> subTaskIds) {
        this.subTaskIds = subTaskIds;
    }
    public void setDuration(Long duration) {

        this.duration = duration;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "{Type: Epic" +
                ", name: " + name +
                ", id: " + id +
                ", description: " + description +
                ", status: " + status +
                ", subTaskIds: " + subTaskIds +
                ", duration: " + Duration.ofMillis(duration)+
                ", startTime: " + startTime.format(formatter) +
                ", endTime: " + endTime.format(formatter) + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Epic)) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTaskIds, epic.subTaskIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTaskIds);
    }
}
