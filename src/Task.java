public class Task {
    private String name;
    private String describe;
    private int id;
    private String status;

    public Task(String name, String describe, String status) {
        this.name = name;
        this.describe = describe;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
                return "Task name - " + name
                + ", describe - " + describe
                + ", id - " + id
                + ", status - " + status ;
    }
}