package com.ex.todolist.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Long id) {
        super("해당 ID의 할 일이 존재하지 않습니다. " + id);
    }
}
