package TuskManager;

import java.util.Objects;

public class SubTusk extends Tusk { //добавил параметр для связи с эпиком
    Integer epicId;
    public SubTusk(String name, Integer id, String description, String status, Integer epicId) {
        super(name, id, description, status);
        this.epicId = epicId;
    }

    public Integer getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "SubTusk{" +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                "epicId=" + epicId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubTusk)) return false;
        if (!super.equals(o)) return false;
        SubTusk subTusk = (SubTusk) o;
        return Objects.equals(epicId, subTusk.epicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }
}
