package com.ex.todolist.service;

import com.ex.todolist.dto.TodoDTO;
import com.ex.todolist.entity.TodoList;
import com.ex.todolist.exception.EntityNotFoundException;
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
        TodoList saved = TodoList.toEntity(todoDTO);

        return TodoDTO.toDTO(todoRepository.save(saved));
    }

    @Transactional(readOnly = true)
    public TodoDTO getTodo(Long id) {
        TodoList todoList = getTodoList(id);

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
        TodoList todoList = getTodoList(id);

        todoList.changeTask(todoDTO.getTask());
        todoList.changeDescription(todoDTO.getDescription());
        todoList.changeDue(todoDTO.getDue());

        return TodoDTO.toDTO(todoList);
    }

    public void deleteTodo(Long id) {
        TodoList todoList = getTodoList(id);
        todoRepository.delete(todoList);
    }

    public void finishTodo(Long id) {
        TodoList todoList = getTodoList(id);

        if ("Completed".equals(todoList.getStatus())) {
            todoList.changeStatus("In progress");
        } else {
            todoList.changeStatus("Completed");
        }
    }

    public List<TodoDTO> searchByTask(String keyword) {
        return todoRepository.findByTask(keyword).stream()
                .map(TodoDTO::toDTO)
                .collect(Collectors.toList());
    }

    private TodoList getTodoList(Long id) {
        return todoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(id));
    }
}