package com.escass.advancedbbs.mappers;

import com.escass.advancedbbs.entities.CommentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper {
    int insertComment(CommentEntity comment);

//    CommentEntity selectCommentById(@Param(value="index") int index);
}
