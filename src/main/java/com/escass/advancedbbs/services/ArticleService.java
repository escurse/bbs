package com.escass.advancedbbs.services;

import com.escass.advancedbbs.entities.ArticleEntity;
import com.escass.advancedbbs.entities.ImageEntity;
import com.escass.advancedbbs.mappers.ArticleMapper;
import com.escass.advancedbbs.mappers.ImageMapper;
import com.escass.advancedbbs.results.article.DeleteArticleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleMapper articleMapper;
    private final ImageMapper imageMapper;

    public DeleteArticleResult deleteArticle(int index, String password) {
        if (index < 1 || password == null || password.length() < 4 || password.length() > 50) {
            return DeleteArticleResult.FAILURE;
        }
        ArticleEntity article = this.articleMapper.selectArticleByIndex(index);
        if (article == null) {
            return DeleteArticleResult.FAILURE;
        }
        if (article.getDeletedAt() != null) {
            return DeleteArticleResult.FAILURE;
        }
        if (!article.getPassword().equals(password)) {
            return DeleteArticleResult.FAILURE_PASSWORD;
        }
        article.setDeletedAt(LocalDateTime.now());
        return this.articleMapper.updateArticle(article) > 0
                ? DeleteArticleResult.SUCCESS
                : DeleteArticleResult.FAILURE;
    }

    public ArticleEntity getArticle(int index) {
        if (index < 1) {
            return null;
        }
        return this.articleMapper.selectArticleByIndex(index);
    }

    public ImageEntity getImage(int index) {
        if (index < 1) {
            return null;
        }
        return this.imageMapper.selectImageByIndex(index);
    }

    public boolean increaseArticleView(ArticleEntity article) {
        if (article == null) {
            return false;
        }
        article.setView(article.getView() + 1);
        return this.articleMapper.updateArticle(article) > 0;
    }

    public boolean modifyArticle(ArticleEntity article, String oldPassword) {
        if (article == null ||
            article.getIndex() < 1 ||
            article.getNickname() == null || article.getNickname().length() < 2 || article.getNickname().length() > 10 ||
            article.getPassword() == null || article.getPassword().length() < 4 || article.getPassword().length() > 50 ||
            article.getTitle() == null || article.getTitle().isEmpty() || article.getTitle().length() > 100 ||
            article.getContent() == null || article.getContent().isEmpty() || article.getContent().length() > 16_777_215 ||
            oldPassword == null || oldPassword.length() < 4 || oldPassword.length() > 50) {
            return false;
        }
        ArticleEntity dbArticle = this.articleMapper.selectArticleByIndex(article.getIndex());
        if (dbArticle == null) {
            return false;
        }
        if (dbArticle.getDeletedAt() != null) {
            return false;
        }
        if (!dbArticle.getPassword().equals(oldPassword)) {
            return false;
        }
        dbArticle.setNickname(article.getNickname());
        dbArticle.setPassword(article.getPassword());
        dbArticle.setTitle(article.getTitle());
        dbArticle.setContent(article.getContent());
        dbArticle.setUpdatedAt(LocalDateTime.now());
        return this.articleMapper.updateArticle(dbArticle) > 0;
    }

    public boolean uploadImage(ImageEntity image) {
        if (image == null ||
            image.getData() == null || image.getData().length == 0 ||
            image.getContentType() == null || image.getContentType().isEmpty() ||
            image.getName() == null || image.getName().isEmpty()) {
            return false;
        }
        image.setCreatedAt(LocalDateTime.now());
        return this.imageMapper.insertImage(image) > 0;
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
