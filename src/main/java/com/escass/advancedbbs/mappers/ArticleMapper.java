package com.escass.advancedbbs.mappers;

import com.escass.advancedbbs.entities.ArticleEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper {
    int insertArticle(ArticleEntity article);
}
