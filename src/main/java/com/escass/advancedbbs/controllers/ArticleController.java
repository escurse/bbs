package com.escass.advancedbbs.controllers;

import com.escass.advancedbbs.entities.BoardEntity;
import com.escass.advancedbbs.services.BoardSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/article")
public class ArticleController {
    private final BoardSerivce boardSerivce;

    @Autowired
    public ArticleController(BoardSerivce boardSerivce) {
        this.boardSerivce = boardSerivce;
    }

    @RequestMapping(value = "/write", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getWrite(@RequestParam(value = "boardId", required = false) String boardId) {
        BoardEntity board = this.boardSerivce.getBoard(boardId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("board", board);
        modelAndView.setViewName("article/write");
        return modelAndView;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
    public String postWrite(@RequestParam(value = "nickname") String nickname, @RequestParam(value = "password") String password,
                            @RequestParam(value = "title") String title, @RequestParam(value = "content") String content) {
        return "article/write";
    }

}
