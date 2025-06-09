package com.ex.todolist.service;

import com.ex.todolist.dto.TodoDTO;
import com.ex.todolist.entity.TodoList;
import com.ex.todolist.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoDTO register(TodoDTO todoDTO) {
        TodoList saved = TodoList.builder()
                .task(todoDTO.getTask())
                .description(todoDTO.getDescription())
                .created(todoDTO.getCreated())
                .due(todoDTO.getDue())
                .build();

        return TodoDTO.toDTO(todoRepository.save(saved));
    }

    @Transactional(readOnly = true)
    public TodoDTO getTodo(Long id) {
        TodoList todoList = todoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않음"));

        return TodoDTO.toDTO(todoList);
    }

    @Transactional(readOnly = true)
    public List<TodoDTO> getAllTodo() {
        return todoRepository.findAll()
                .stream()
                .map(TodoDTO::toDTO)
                .collect(Collectors.toList());
    }

    public TodoDTO updateTodo(Long id, TodoDTO todoDTO) {
        TodoList todoList = todoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않음"));

        todoList.changeTask(todoDTO.getTask());
        todoList.changeDescription(todoDTO.getDescription());
        todoList.changeDue(todoDTO.getDue());

        return TodoDTO.toDTO(todoList);
    }

    public void deleteTodo(Long id) {
        TodoList todoList = todoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않음"));
        todoRepository.delete(todoList);
    }
}