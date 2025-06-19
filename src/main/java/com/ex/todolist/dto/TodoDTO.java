package com.ex.todolist.dto;

import com.ex.todolist.entity.TodoList;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {

    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9가-힣\\s]{1,50}$", message = "할 일(task)은 한글, 영문, 숫자 포함 1~50자 이내여야 합니다.")
    @NotBlank(message = "할 일(Task)이 비어있습니다.")
    private String task;

    @Pattern(regexp = "^[a-zA-Z0-9가-힣\\s]{1,50}$", message = "설명(description)은 특수문자 .,!?-()를 포함하여 200자 이내여야 합니다.")
    @NotBlank(message = "설명이 비어있습니다.")
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
