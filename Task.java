public class Task {
    private String title;
    private String description;
    private Double creationDate;
    private boolean status;

    public Task (String title, String description) {
        this.title = title;
        this.description = description;
        this.creationDate = DateOps.currentTimeUnix();
        this.status = false;
    }

    public Double getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Double creationDate) {
        this.creationDate = creationDate;
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
