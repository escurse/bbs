package com.escass.advancedbbs.mappers;

import com.escass.advancedbbs.entities.CommentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper {
    int insertComment(CommentEntity comment);
    int updateComment(CommentEntity comment);

    CommentEntity selectCommentByIndex(@Param("index") int index);

    CommentEntity[] selectCommentByArticleIndex(@Param("articleIndex") int articleIndex);
}
