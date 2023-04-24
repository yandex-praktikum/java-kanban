import java.util.ArrayList;

public class Epic {
    private String name;
    private String describe;
    private String status;
    private ArrayList<Integer> idSubtask = new ArrayList<>();
    private int id;

    public Epic(String name, String describe) {
        this.name = name;
        this.describe = describe;
    }

    public ArrayList<Integer> getIdSubtask() {
        return idSubtask;
    }

    public int getId() {
        return id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Epic's name - " + name
                + ", describe - " + describe
                + ", id - " + id
                + ", status - " + status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdSubtask(ArrayList<Integer> idSubtask) {
        this.idSubtask = idSubtask;
    }

}