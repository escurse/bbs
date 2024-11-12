package com.escass.advancedbbs.mappers;

import com.escass.advancedbbs.entities.ArticleEntity;
import com.escass.advancedbbs.vos.ArticleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleMapper {
    int insertArticle(ArticleEntity article);
    int updateArticle(ArticleEntity article);

    int selectArticleCountByBoardId(@Param("boardId") String boardId);

    ArticleEntity selectArticleByIndex(@Param("index") int index);

    int selectArticleCountBySearch(@Param("boardId") String boardId,
                                   @Param("filter") String filter,
                                   @Param("keyword") String keyword);

    ArticleVo[] selectArticleByBoardId(@Param("boardId") String boardId,
                                       @Param("limitCount") int limitCount,
                                       @Param("offsetCount") int offsetCount);

    ArticleVo[] selectArticlesBySearch(@Param("boardId") String boardId,
                                       @Param("filter") String filter,
                                       @Param("keyword") String keyword,
                                       @Param("limitCount") int limitCount,
                                       @Param("offsetCount") int offsetCount);
}
