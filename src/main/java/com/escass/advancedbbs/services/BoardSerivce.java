package com.escass.advancedbbs.services;

import com.escass.advancedbbs.entities.BoardEntity;
import org.springframework.stereotype.Service;

@Service
public class BoardSerivce {
    public BoardEntity getBoard(String id) {
        if (id == null || id.isEmpty() || id.length() > 10) {
            return null;
        }
        return null;
    }
}
