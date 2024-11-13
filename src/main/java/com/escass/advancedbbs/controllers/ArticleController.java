package com.escass.advancedbbs.controllers;

import com.escass.advancedbbs.entities.ArticleEntity;
import com.escass.advancedbbs.entities.BoardEntity;
import com.escass.advancedbbs.entities.ImageEntity;
import com.escass.advancedbbs.results.article.DeleteArticleResult;
import com.escass.advancedbbs.services.ArticleService;
import com.escass.advancedbbs.services.BoardSerivce;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping(value = "/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final BoardSerivce boardSerivce;

    @RequestMapping(value = "/image", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@RequestParam(value = "index", required = false, defaultValue = "0") int index) {
        ImageEntity image = this.articleService.getImage(index);
        if (image == null) {
            return ResponseEntity.notFound().build();   // 응답 내용 없음, 상태 코드는 404(Not Found)인 상태로 응답을 내보냄.
        }
        return ResponseEntity
                .ok()                                                               // 응답 상태 코드 200
                .contentLength(image.getData().length)                              // 응답 내용 길이 설정
                .contentType(MediaType.parseMediaType(image.getContentType()))      // 응답 MIME 타입 설정
                .body(image.getData());                                             // 응답 내용 설정
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String postImage(@RequestParam(value = "upload") MultipartFile file) throws IOException {
        ImageEntity image = new ImageEntity();
        image.setData(file.getBytes());
        image.setContentType(file.getContentType());
        image.setName(file.getOriginalFilename());
        JSONObject response = new JSONObject();
        boolean result = this.articleService.uploadImage(image);
        if (result) {
            response.put("url", "/article/image?index=" + image.getIndex());
        }
        return response.toString();
    }

    @RequestMapping(value = "/modify", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getModify(@RequestParam(value = "index", required = false, defaultValue = "0") int index,
                                  @RequestParam(value = "password", required = false) String password) {
        ModelAndView modelAndView = new ModelAndView();
        ArticleEntity article = this.articleService.getArticle(index);
        if (article != null) {
            if (!article.getPassword().equals(password)) {
                article = null;
            }
        }
        if (article != null) {
            BoardEntity board = this.boardSerivce.getBoard(article.getBoardId());
            modelAndView.addObject("article", article);
            modelAndView.addObject("board", board);
            if (board != null) {
                modelAndView.addObject("boards", this.boardSerivce.getBoards());
            }
        }
        modelAndView.setViewName("article/modify");
        return modelAndView;
    }

    @RequestMapping(value = "/modify", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String patchModify(@RequestParam(value = "oldPassword", required = false) String oldPassword, ArticleEntity article) {
        boolean result = this.articleService.modifyArticle(article, oldPassword);
        JSONObject response = new JSONObject();
        response.put("result", result);
        return response.toString();
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getRead(@RequestParam(value = "index", required = false, defaultValue = "0") int index) {
        ModelAndView modelAndView = new ModelAndView();
        ArticleEntity article = this.articleService.getArticle(index);
        modelAndView.addObject("article", article);
        if (article != null) {
            this.articleService.increaseArticleView(article);
            BoardEntity board = this.boardSerivce.getBoard(article.getBoardId());
            modelAndView.addObject("board", board);
            modelAndView.addObject("boards", this.boardSerivce.getBoards());
        }
        modelAndView.setViewName("article/read");
        return modelAndView;
    }

    @RequestMapping(value = "/read", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteRead(@RequestParam(value = "index", required = false, defaultValue = "0") int index,
                             @RequestParam(value = "password", required = false) String password) {
        DeleteArticleResult result = this.articleService.deleteArticle(index, password);
        JSONObject response = new JSONObject();
        response.put("result", result.name().toLowerCase());
        return response.toString();
    }

    @RequestMapping(value = "/write", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getWrite(@RequestParam(value = "boardId", required = false) String boardId) {
        BoardEntity board = this.boardSerivce.getBoard(boardId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("board", board);
        if (board != null) {
            modelAndView.addObject("boards", this.boardSerivce.getBoards());
        }
        modelAndView.setViewName("article/write");
        return modelAndView;
    }

    @RequestMapping(value = "/write", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postWrite(ArticleEntity article) {
        boolean result = this.articleService.write(article);
        JSONObject response = new JSONObject();
        response.put("result", result);
        if (result) {
            response.put("index", article.getIndex());
        }
        return response.toString();
    }
}
