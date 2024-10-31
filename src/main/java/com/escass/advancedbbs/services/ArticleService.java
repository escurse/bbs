package com.escass.advancedbbs.services;

import com.escass.advancedbbs.entities.ArticleEntity;
import com.escass.advancedbbs.mappers.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private final ArticleMapper articleMapper;

    @Autowired
    public ArticleService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
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
        return false;
    }
}
