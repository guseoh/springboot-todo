package com.ex.todolist.dto;

import com.ex.todolist.entity.TodoList;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {

    private Long id;
    private String task;
    private String description;
    private String status;
    private LocalDate created;
    private LocalDate due;

    // Entity -> DTO
    public static TodoDTO toDTO(TodoList todoList) {
        return TodoDTO.builder()
                .id(todoList.getId())
                .task(todoList.getTask())
                .description(todoList.getDescription())
                .status(todoList.getStatus())
                .created(LocalDate.now())
                .due(todoList.getDue())
                .build();
    }
}
