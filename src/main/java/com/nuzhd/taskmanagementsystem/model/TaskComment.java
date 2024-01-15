package com.nuzhd.taskmanagementsystem.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
public class TaskComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    private Long author;
    private LocalDateTime createdAt;
    private Long targetTaskId;

    public TaskComment() {
    }

    public TaskComment(String content, Long author, Long targetTaskId) {
        this.content = content;
        this.author = author;
        this.createdAt = LocalDateTime.now();
        this.targetTaskId = targetTaskId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAuthorId() {
        return author;
    }

    public void setAuthorId(Long authorId) {
        this.author = authorId;
    }

    public Long getTargetTaskId() {
        return targetTaskId;
    }

    public void setTargetTaskId(Long targetTaskId) {
        this.targetTaskId = targetTaskId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
