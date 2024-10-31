package com.escass.advancedbbs.controllers;

import com.escass.advancedbbs.entities.ArticleEntity;
import com.escass.advancedbbs.entities.BoardEntity;
import com.escass.advancedbbs.services.ArticleService;
import com.escass.advancedbbs.services.BoardSerivce;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final BoardSerivce boardSerivce;

    @RequestMapping(value="/read", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getRead(@RequestParam(value="index", required = false, defaultValue = "0") int index) {
        ModelAndView modelAndView = new ModelAndView();
        ArticleEntity article = this.articleService.getArticle(index);
        modelAndView.addObject("article", article);
        modelAndView.setViewName("article/read");
        return modelAndView;
    }

    @RequestMapping(value = "/write", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getWrite(@RequestParam(value = "boardId", required = false) String boardId) {
        BoardEntity board = this.boardSerivce.getBoard(boardId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("board", board);
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
