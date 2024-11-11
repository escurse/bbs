package com.escass.advancedbbs.services;

import com.escass.advancedbbs.entities.CommentEntity;
import com.escass.advancedbbs.mappers.CommentMapper;
import com.escass.advancedbbs.results.article.DeleteArticleResult;
import com.escass.advancedbbs.results.comment.DeleteCommentResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper commentMapper;

    public DeleteCommentResult deleteArticle(int index, String password) {
        // 댓글 삭제를 위한 서비스 로직(게시글 삭제와 로직이 같다)
        // 1. 전달 받은 변수에 대한 정규화 실시(index가 1이상인지, password가 null은 아닌지, 길이는 옳은지 등등)
        // 2. 전달 받은 index를 index로 가지는 댓글(CommentEntity)이 DB에 존재하는지 SELECT해오기.
        // 3. <2>가 없다면 실패로 반환.
        // 4. <2>가 있다면, 이의 'deletedAt' 값을 확인하고 이가 null이 아니라면 실패로 반환.(이미 삭제된 것임으로)
        // 5. <2>의 'password'와 전달받은 'password'가 일치하는지 확인. 불일치 한다면 실패로 반환.
        // 6. <2>의 'deletedAt'을 현재 일시로 지정.
        // 7. <2>를 UPDATE하고 결과 값이 0을 초과하는가의 여부에 따라 성공 및 실패 값 반환.
        // 8.

        if (index < 1 || password == null || password.length() < 4 || password.length() > 50) {
            return DeleteCommentResult.FAILURE;
        }
        CommentEntity comment = this.commentMapper.selectCommentByIndex(index);

        if (comment == null) {
            return DeleteCommentResult.FAILURE;
        }
        if (comment.getDeletedAt() != null) {
            return DeleteCommentResult.FAILURE;
        }
        if (!comment.getPassword().equals(password)) {
            return DeleteCommentResult.FAILURE_PASSWORD;
        }
        comment.setDeletedAt(LocalDateTime.now());
        return this.commentMapper.updateComment(comment) > 0
                ? DeleteCommentResult.SUCCESS
                : DeleteCommentResult.FAILURE;
    }


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
