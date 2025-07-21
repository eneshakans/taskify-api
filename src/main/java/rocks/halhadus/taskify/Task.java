package rocks.halhadus.taskify;

public class Task {
    private String title;
    private String description;
    private final Double creationDate;
    private boolean status;

    public Task (String title, String description) {
        this.title = title;
        this.description = description;
        this.creationDate = DateOps.currentTimeUnix();
        this.status = false;
    }

    public Task (String title, String description, Double creationDate, boolean status) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.status = status;
    }

    public Double getCreationDate() {
        return creationDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
