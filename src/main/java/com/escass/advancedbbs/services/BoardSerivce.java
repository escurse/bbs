package com.escass.advancedbbs.services;

import com.escass.advancedbbs.entities.BoardEntity;
import com.escass.advancedbbs.mappers.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardSerivce {
    private final BoardMapper boardMapper;

    @Autowired
    public BoardSerivce(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    public BoardEntity getBoard(String id) {
        if (id == null || id.isEmpty() || id.length() > 10) {
            return null;
        }
        return this.boardMapper.selectBoardById(id);
    }
}
