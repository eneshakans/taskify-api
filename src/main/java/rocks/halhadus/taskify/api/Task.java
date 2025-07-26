package rocks.halhadus.taskify.api;

import java.util.UUID;

public class Task {
    private String id;
    private String title;
    private String description;
    private final Double creationDate;
    private boolean status;

    public Task(String title, String description) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.creationDate = (double) System.currentTimeMillis();
        this.status = false;
    }

    public Task(String id, String title, String description, Double creationDate, boolean status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.status = status;
    }

    public String getId() { return id; }
    public Double getCreationDate() { return creationDate; }
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}