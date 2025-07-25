package com.ex.todolist.repository;

import com.ex.todolist.entity.TodoList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<TodoList, Long> {

    Page<TodoList> findByTaskContaining(String keyword, Pageable pageable);
}
