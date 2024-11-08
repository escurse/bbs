package com.escass.advancedbbs.services;

import com.escass.advancedbbs.entities.CommentEntity;
import com.escass.advancedbbs.mappers.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper commentMapper;

    public CommentEntity[] getCommentsByArticleIndex(int articleIndex) {
        if (articleIndex < 1) {
            return null;
        }
        return this.commentMapper.selectCommentByArticleIndex(articleIndex);
    }

    public boolean writeComment(CommentEntity comment) {
        if (comment == null ||
            comment.getArticleIndex() < 1 ||
            (comment.getCommentIndex() != null && comment.getCommentIndex() < 1) ||
            comment.getNickname() == null || comment.getNickname().isEmpty() || comment.getNickname().length() > 10 ||
            comment.getPassword() == null || comment.getPassword().length() < 4 || comment.getPassword().length() > 50 ||
            comment.getContent() == null || comment.getContent().isEmpty() || comment.getContent().length() > 100) {
            return false;
        }
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(null);
        comment.setDeletedAt(null);
        return this.commentMapper.insertComment(comment) > 0;
    }
}
