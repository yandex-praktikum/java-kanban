package Task;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    private List<Integer> subTaskIds = new ArrayList<>();
    public Epic(String name, Integer id, String description) {
        super(name, id, description, Status.NEW);
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

    @Override
    public String toString() {
        return "Epic{" +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                "subTaskIds=" + subTaskIds +
                '}';
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
