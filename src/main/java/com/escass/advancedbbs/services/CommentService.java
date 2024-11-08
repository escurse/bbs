package com.escass.advancedbbs.services;

import com.escass.advancedbbs.entities.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    public boolean writeComment(CommentEntity comment) {
        if (comment == null ||
            comment.getArticleIndex() < 1 ||
            (comment.getCommentIndex() != null && comment.getCommentIndex() < 1) ||
            comment.getNickname() == null || comment.getNickname().isEmpty() || comment.getNickname().length() > 10 ||
            comment.getPassword() == null || comment.getPassword().length() < 4 || comment.getPassword().length() > 50 ||
            comment.getContent() == null || comment.getContent().isEmpty() || comment.getContent().length() > 100) {
            return false;
        }
        return true;
    }
}
