package com.ex.todolist.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Todolist")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@EntityListeners(AuditingEntityListener.class)
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String task; // 할 일

    @Column
    private String description; // 설명

    @Column
    @Builder.Default
    private String status = "In progress"; // 상태

    @CreatedDate
    private LocalDate created;  // 생성 날짜

    private LocalDate due; // 마감일

    public void changeTask(String task) {
        this.task = task;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeDue(LocalDate due) {
        this.due = due;
    }

    public void changeStatus(String status) {
        this.status = status;
    }

}
