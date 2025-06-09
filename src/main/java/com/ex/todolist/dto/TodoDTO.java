package com.ex.todolist.dto;

import com.ex.todolist.entity.TodoList;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {

    private Long id;
    private String task;
    private String description;
    private LocalDateTime created;
    private LocalDate due;

    // Entity -> DTO
    public static TodoDTO toDTO(TodoList todoList) {
        return TodoDTO.builder()
                .id(todoList.getId())
                .task(todoList.getTask())
                .description(todoList.getDescription())
                .created(todoList.getCreated())
                .due(todoList.getDue())
                .build();
    }

    // DTO -> Entity
    public TodoList toEntity() {
        return TodoList.builder()
                .id(id)
                .task(task)
                .description(description)
                .created(created)
                .due(due)
                .build();
    }

}
