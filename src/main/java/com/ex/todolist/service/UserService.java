package com.ex.todolist.service;

import com.ex.todolist.dto.JoinRequest;
import com.ex.todolist.dto.LoginRequest;
import com.ex.todolist.entity.User;
import com.ex.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean checkUserNameDuplicate(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean checkNickNameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    public void join(JoinRequest req) {
        userRepository.save(req.toEntity());
    }

    // 아이디 확인 -> 비밀번호 확인
    public User login(LoginRequest req) {

        Optional<User> find = userRepository.findByUsername(req.getUsername());

        if (find.isEmpty()) {
            return null;
        }

        User user = find.get();

        if (!user.getPassword().equals(req.getPassword())) {
            return null;
        }

        return user;
    }

    public User getLoginUserById(Long id) {

        if(id == null) return null;

        Optional<User> find = userRepository.findById(id);

        return find.orElse(null);
    }
}
