package com.ex.todolist.repository;

import com.ex.todolist.entity.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoList, Long> {
}
