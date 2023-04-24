public class SubTask extends Task {
    int epicId;

    public SubTask(String name, String describe, String status) {
        super(name, describe, status);
    }


    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "SubTask name - " + getName()
                + ", subDescribe - " + getDescribe()
                + ", subId - " + getId()
                + ", subStatus - " + getStatus()
                ;
    }

}
