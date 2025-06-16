package com.ex.todolist.repository;

import com.ex.todolist.dto.TodoDTO;
import com.ex.todolist.entity.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<TodoList, Long> {

    List<TodoList> findByTask(String task);
}
