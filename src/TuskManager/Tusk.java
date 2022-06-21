package TuskManager;

import java.util.Objects;

class Tusk { // класс родитель
    String name; // название задачи
    Integer id; // идентификационный номер (будет ключём в мапе)
    String description; // описание задачи
    final String status; // статус задачи NEW IN_PROGRESS DONE

    protected Tusk(String name, Integer id, String description, String status) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.status = status;
    }



    @Override
    public String toString() {
        return "TuskManager.Tusk{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tusk)) return false;
        Tusk tusk = (Tusk) o;
        return Objects.equals(name, tusk.name) && Objects.equals(id, tusk.id) && Objects.equals(description, tusk.description) && Objects.equals(status, tusk.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, description, status);
    }
}
