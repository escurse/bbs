package com.escass.advancedbbs.mappers;

import com.escass.advancedbbs.entities.BoardEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardMapper {
    BoardEntity selectBoardById(@Param("id") String id);

    BoardEntity[] selectBoards();
}
