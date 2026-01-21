package ru.yandex.practicum.catsgram.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@EqualsAndHashCode(of = { "id" })
public class Post {
    /** Уникальный идентификатор поста. */
    private Long id;

    /** Идентификатор автора поста. */
    private long authorId;

    /** Текстовое описание поста. */
    private String description;

    /** Дата и время публикации поста. */
    private Instant postDate;
}
