package com.escass.advancedbbs.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = {"index"})
public class ArticleEntity {
    private int index;
    private String boardId;
    private String nickname;
    private String password;
    private String title;
    private String content;
    private int view;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
