package com.example.THBack.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import com.example.THBack.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="tb_task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="email_author", nullable = false)
    private String email;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="description", nullable = false)
    private String description;

    @Column(name="priority", nullable = false)
    private String priority;

    @Column(name="email_executor", nullable = false)
    private String emailExecutor;

    @Column(name="date_task", nullable = false)
    private LocalDate date;

    @Column(name="time_create", nullable = false)
    private LocalDateTime timeCreate;

    @Column(name="state", nullable = false)
    private State state;

    @Column(name="time_update", nullable = false)
    private LocalDateTime timeUpdate;

    public Task(String email, String name, String description, String priority, String emailExecutor, LocalDate date, LocalDateTime timeCreate, State state, LocalDateTime timeUpdate) {
        this.email = email;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.emailExecutor = emailExecutor;
        this.date = date;
        this.timeCreate = timeCreate;
        this.state = state;
        this.timeUpdate = timeUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(email, task.email) && Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(priority, task.priority) && Objects.equals(emailExecutor, task.emailExecutor) && Objects.equals(date, task.date) && Objects.equals(timeCreate, task.timeCreate) && state == task.state && Objects.equals(timeUpdate, task.timeUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, description, priority, emailExecutor, date, timeCreate, state, timeUpdate);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", emailExecutor='" + emailExecutor + '\'' +
                ", date=" + date +
                ", timeCreate=" + timeCreate +
                ", state=" + state +
                ", timeUpdate=" + timeUpdate +
                '}';
    }
}