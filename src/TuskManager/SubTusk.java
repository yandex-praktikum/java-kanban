package TuskManager;

class SubTusk extends Tusk { //добавил параметр для связи с эпиком
    Integer epicId;
    protected SubTusk(String name, Integer id, String description, String status, Integer epicId) {
        super(name, id, description, status);
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "TuskManager.SubTusk{" +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                "epicId=" + epicId +
                '}';
    }
}
