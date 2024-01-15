package com.nuzhd.taskmanagementsystem.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;
    @Enumerated(value = EnumType.STRING)
    private TaskPriority priority;
    private Long authorId;
    private Long assigneeId;
    @OneToMany(mappedBy = "targetTaskId", fetch = FetchType.EAGER)
    private Set<TaskComment> comments;

    public Task() {
    }

    public Task(String title, String description, TaskPriority priority, Long authorId, Long assigneeId) {
        this.title = title;
        this.description = description;
        this.authorId = authorId;
        this.assigneeId = assigneeId;
        this.priority = priority;
        this.status = TaskStatus.NEW;
    }

    public Task(String title, String description, TaskPriority priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = TaskStatus.NEW;
    }

    public Task(String title, String description, TaskPriority priority, Long assigneeId) {
        this.title = title;
        this.description = description;
        this.status = TaskStatus.NEW;
        this.priority = priority;
        this.assigneeId = assigneeId;
        this.comments = new HashSet<>();
    }

    public Task(String title, String description, TaskStatus status, TaskPriority priority, Long assigneeId) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.assigneeId = assigneeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
    }

    public Set<TaskComment> getComments() {
        return comments;
    }

    public void setComments(Set<TaskComment> comments) {
        this.comments = comments;
    }
}
