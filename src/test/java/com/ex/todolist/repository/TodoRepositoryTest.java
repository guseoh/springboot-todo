package com.ex.todolist.repository;

import com.ex.todolist.entity.TodoList;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class TodoRepositoryTest {


    @Autowired
    private TodoRepository todoRepository;

    @Test
    void testInsert() {
        TodoList todoList = TodoList.builder()
                .task("테스트 할 일1")
                .description("테스트 설명1")
                .created(LocalDateTime.now())
                .due(LocalDate.now())
                .build();

        TodoList saved = todoRepository.save(todoList);

        System.out.println(saved);
    }

    @Test
    void testRead() {

        Long idx = 1L;

        Optional<TodoList> find = todoRepository.findById(idx);

        System.out.println(find);
    }

    @Test
    @Commit
    void testUpdate() {

        Long idx = 1L;

        TodoList todoList = todoRepository.findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("할 일 ID " + idx + " 없음"));

//        Optional<TodoList> res = todoRepository.findById(idx);
//
//        TodoList todoList = res.get();

        System.out.println(todoList);

        todoList.changeTask("Change Task");
        todoList.changeDescription("Change Description");
        todoList.changeDue(LocalDate.now().plusDays(3));


        Assertions.assertEquals("Change Task", todoList.getTask());
        Assertions.assertEquals("Change Description", todoList.getDescription());
        Assertions.assertEquals(LocalDate.now().plusDays(3), todoList.getDue());

    }

    @Test
    void testDelete() {

        Long idx = 1L;

        TodoList todoList = todoRepository.findById(idx).orElseThrow(
                () -> new IllegalArgumentException("해당 Id " + idx + " 없음")
        );

        todoRepository.delete(todoList);

    }
}