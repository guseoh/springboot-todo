package com.ex.todolist.service;

import com.ex.todolist.dto.TodoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
@Commit
class TodoServiceTest {

    @Autowired
    private TodoService service;

    @Test
    void testInsert() {
        for (int i = 1; i <= 10; i++) {
            TodoDTO todoDTO = TodoDTO.builder()
                    .task("Test Todo" + i)
                    .description("Test description" + i)
                    .created(LocalDateTime.now())
                    .due(LocalDate.now().plusDays(3))
                    .build();
            TodoDTO result = service.register(todoDTO);

            System.out.println(result);
        }
    }

    @Test
    void testRead() {

        Long id = 25L;

        TodoDTO todoDTO = service.getTodo(id);

        System.out.println(todoDTO);
    }

    @Test
    void testAllRead() {
        List<TodoDTO> allTodos = service.getAllTodo();

        System.out.println(allTodos);
    }

    @Test
    void testUpdate() {

        Long id = 30L;

        TodoDTO todoDTO = TodoDTO.builder()
                .task("수정 후")
                .description("수정 후")
                .due(LocalDate.now().plusDays(5))
                .build();

        service.updateTodo(id, todoDTO);

        System.out.println(service.getTodo(id));

    }

    @Test
    void testDelete() {

        Long id = 23L;

        service.deleteTodo(id);
    }

}