package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.DuplicatedDataException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.model.User;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        // проверяем выполнение необходимых условий
        if (user.getEmail().isBlank()) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        }
        for (User u : users.values()) {
            if (u.getEmail().equals(user.getEmail())) {
                throw new DuplicatedDataException("Этот имейл уже используется");
            }
        }
        // формируем дополнительные данные
        user.setId(getNextId());
        user.setRegistrationDate(Instant.now());
        users.put(user.getId(), user);
        return user;
    }

    // вспомогательный метод для генерации идентификатора нового юзера
    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    @PutMapping
    public User update(@RequestBody User newUser) {
        if (newUser.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }

        if (!users.containsKey(newUser.getId())) {
            throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден");
        }

        User oldUser = users.get(newUser.getId());

        if (newUser.getUsername() != null) {
            oldUser.setUsername(newUser.getUsername());
        }

        if (newUser.getEmail() != null) {
            boolean emailTakenByOther = users.values().stream()
                    .anyMatch(u -> !u.getId().equals(newUser.getId()) &&
                            u.getEmail().equals(newUser.getEmail()));
            if (emailTakenByOther) {
                throw new DuplicatedDataException("Этот имейл уже используется");
            }
            oldUser.setEmail(newUser.getEmail());
        }

        if (newUser.getPassword() != null) {
            oldUser.setPassword(newUser.getPassword());
        }

        return oldUser;
    }
}
