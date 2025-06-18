package com.ex.todolist.controller;

import com.ex.todolist.dto.JoinRequest;
import com.ex.todolist.dto.LoginRequest;
import com.ex.todolist.entity.User;
import com.ex.todolist.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping(value = {"", "/"})
    public String home(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest,
                        BindingResult bindingResult,
                        HttpServletRequest httpServletRequest) {

        User user = userService.login(loginRequest);

        // Todo: 수정 or 제거
        if (user == null) {
            log.info("로그인 실패.....................");
            bindingResult.reject("loginFail", "로그인 아이디 또는 비밀번호가 틀렸습니다.");
        }

        if (bindingResult.hasErrors()) {
            return "user/login";
        }

        log.info("로그인 성공..........................");
        httpServletRequest.getSession().invalidate(); // 세션 초기화
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("id", user.getId());
        session.setMaxInactiveInterval(1800);

        return "redirect:/user/login";
    }

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("joinRequest", new JoinRequest());

        return "user/join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute JoinRequest joinRequest, BindingResult bindingResult, Model model) {

        if (userService.checkUserNameDuplicate(joinRequest.getUsername())) {
            bindingResult.addError(new FieldError("joinRequest", "username", "로그인 아이디가 중복됩니다."));
        }

        if (userService.checkNickNameDuplicate(joinRequest.getNickname())) {
            bindingResult.addError(new FieldError("joinRequest", "nickname", "닉네임이 중복됩니다."));
        }

        if (userService.checkEmailDuplicate(joinRequest.getEmail())) {
            bindingResult.addError(new FieldError("joinRequest", "email", "이메일이 중복됩니다."));
        }

        if (!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())) {
            bindingResult.addError(new FieldError("joinRequest", "passwordCheck", "비밀번호가 일치하지 않습니다."));
        }

        if (bindingResult.hasErrors()) {
            log.info("회원 가입 실패.........................");
            return "user/join";
        }

        userService.join(joinRequest);
        log.info("회원 가입 성공...........................");

        return "redirect:/user/login";
    }
}
