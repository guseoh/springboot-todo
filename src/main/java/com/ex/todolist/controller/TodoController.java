package com.ex.todolist.controller;

import com.ex.todolist.dto.TodoDTO;
import com.ex.todolist.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("todoList", todoService.getAllTodo());
        return "index";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute TodoDTO todoDTO) {
        log.info("투두리스트 등록......................");
        todoService.register(todoDTO);
        return "redirect:/todo/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        log.info("제거 완료....................");
        todoService.deleteTodo(id);

        return "redirect:/todo/";
    }

    @GetMapping("/finish/{id}")
    public String finish(@PathVariable("id") Long id) {

        log.info("할 일 완료........................");
        todoService.finishTodo(id);

        return "redirect:/todo/";
    }

    @GetMapping("/edit/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        TodoDTO todoDTO = todoService.getTodo(id);
        model.addAttribute("todo", todoDTO);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute("todo") TodoDTO todoDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            log.info("수정 실패.............................");
            //bindingResult.addError(new FieldError("todoDTO", "task", ));
            model.addAttribute("todo", todoDTO);
            return "edit";
        }
        log.info("수정 완료.......................");
        todoService.updateTodo(id, todoDTO);

        return "redirect:/todo/";
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        log.info("검색 완료........................");
        List<TodoDTO> result = todoService.searchByTask(keyword);
        model.addAttribute("todolist", result);
        return "todo";
    }
}
