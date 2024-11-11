package com.escass.advancedbbs.vos;

import com.escass.advancedbbs.entities.ArticleEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleVo extends ArticleEntity {
    private int commentCount;
}
