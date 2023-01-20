package models;

import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Subtask> subtasks;

    public Epic(String name, String description) {
        super(name, description, Status.fresh);
        this.subtasks = new ArrayList<>();
    }

    public Epic(String name, String description, int id, String status, ArrayList<Subtask> subtasks) {
        super(name, description, id, status);
        this.subtasks = subtasks;
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void append(Subtask subtask) {
        subtasks.add(subtask);
    }

    public void delete(Subtask subtask) {
        subtasks.remove(subtask);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                ", subtasks=" + subtasks +
                '}';
    }


}