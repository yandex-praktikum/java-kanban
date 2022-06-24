package Task;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
protected ArrayList<Integer> subTaskIds = new ArrayList<>();
   public Epic(String name, Integer id, String description, String status) {
       super(name, id, description, status);
    }
    public void deleteSubTaskIds() { // очистить список айди сабтасков
       subTaskIds.clear();
    }

    public void addSubTaskIds(int id){ // добавить айди в список при добавлении новой сабтаски
       subTaskIds.add(id);
    }

    public void deleteSubTaskFromList(int id){ //
                subTaskIds.remove(id);
    }

    public int getSubTaskId(int id) {
        return subTaskIds.get(id);
    }

    public ArrayList<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void setSubTaskIds(ArrayList<Integer> subTaskIds) {
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
