package com.ex.todolist.controller;

import com.ex.todolist.dto.TodoDTO;
import com.ex.todolist.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("")
    public String index(@RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "10") int size,
                        Model model) {
        Page<TodoDTO> result = todoService.getTodoPage(page - 1, size);
        model.addAttribute("todoPage", result);
        model.addAttribute("todoList", result.getContent());

        int current = result.getNumber() + 1;
        int start = 1;
        int end = Math.min(5, result.getTotalPages());

        model.addAttribute("page", current);
        model.addAttribute("start", start);
        model.addAttribute("end", end);

        return "/index";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute TodoDTO todoDTO) {
        log.info("해당 task = {} 등록 완료......................", todoDTO.getTask());
        todoService.register(todoDTO);
        return "redirect:/todo";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        log.info("해당 id = {} 제거 왼료......................", id);
        todoService.deleteTodo(id);

        return "redirect:/todo";
    }

    @GetMapping("/finish/{id}")
    public String finish(@PathVariable("id") Long id) {

        log.info("해당 id = {} In Progress -> Completed......................", id);
        todoService.finishTodo(id);

        return "redirect:/todo";
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
            log.info("해당 id = {}, 해당 task = {} 수정 실패......................", id, todoDTO.getTask());
            model.addAttribute("todo", todoDTO);
            return "edit";
        }
        log.info("해당 id = {}, 해당 task = {} 수정 완료......................", id, todoDTO.getTask());
        todoService.updateTodo(id, todoDTO);

        return "redirect:/todo";
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        log.info("해당 keyword = {} 검색 완료...................... ", keyword);
        List<TodoDTO> result = todoService.searchByTask(keyword);
        model.addAttribute("todolist", result);
        return "todo";
    }
}
