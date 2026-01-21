package ru.yandex.practicum.catsgram.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = { "id" })
public class Image {
    /** Идентификатор изображения. */
    private Long id;

    /** Идентификатор поста, к которому прикреплено изображение. */
    private long postId;

    /** Оригинальное имя файла изображения. */
    private String originalFileName;

    /** Путь к файлу на диске или в хранилище. */
    private String filePath;
}
