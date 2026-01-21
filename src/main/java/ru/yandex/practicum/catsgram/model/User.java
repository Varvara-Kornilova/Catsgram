package ru.yandex.practicum.catsgram.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@EqualsAndHashCode(of = { "email" })
public class User {
    /** Уникальный идентификатор пользователя. */
    private Long id;

    /** Уникальное имя пользователя. */
    private String username;

    /** Адрес электронной почты. */
    private String email;

    /** Пароль пользователя (в реальном проекте — хэш). */
    private String password;

    /** Дата и время регистрации. */
    private Instant registrationDate;
}
