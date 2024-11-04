package com.escass.advancedbbs.services;

import com.escass.advancedbbs.entities.ArticleEntity;
import com.escass.advancedbbs.mappers.ArticleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleMapper articleMapper;

    public ArticleEntity getArticle(int index) {
        if (index < 1) {
            return null;
        }
        return this.articleMapper.selectArticleByIndex(index);
    }

    public boolean increaseArticleView(ArticleEntity article) {
        if (article == null) {
            return false;
        }
        article.setView(article.getView() + 1);
        return this.articleMapper.updateArticle(article) > 0;
    }

    public boolean write(ArticleEntity article) {
        if (article == null ||
            article.getBoardId() == null ||
            article.getNickname() == null || article.getNickname().length() < 2 || article.getNickname().length() > 10 ||
            article.getPassword() == null || article.getPassword().length() < 4 || article.getPassword().length() > 50 ||
            article.getTitle() == null || article.getTitle().isEmpty() || article.getTitle().length() > 100 ||
            article.getContent() == null || article.getContent().isEmpty() || article.getContent().length() > 16_777_215) {
            return false;
        }
        article.setCreatedAt(LocalDateTime.now());
        article.setView(0);
        article.setUpdatedAt(null);
        article.setDeletedAt(null);
        int affectedRows = this.articleMapper.insertArticle(article);
        return affectedRows > 0;
    }
}
