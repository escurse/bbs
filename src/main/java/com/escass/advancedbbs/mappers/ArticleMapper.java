package com.escass.advancedbbs.mappers;

import com.escass.advancedbbs.entities.ArticleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleMapper {
    int insertArticle(ArticleEntity article);
    int updateArticle(ArticleEntity article);

    ArticleEntity selectArticleByIndex(@Param("index") int index);
}
