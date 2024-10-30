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
@RequestMapping(value ="/board")
public class BoardController {
    private final BoardSerivce boardSerivce;

    @Autowired
    public BoardController(BoardSerivce boardSerivce) {
        this.boardSerivce = boardSerivce;
    }

    @RequestMapping(value ="/list", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getList(@RequestParam(value = "id", required = false) String id) {
        BoardEntity board = this.boardSerivce.getBoard(id);
//        if (board == null) {
//            System.out.println("그런 게시판 없음");
//        } else {
//            System.out.println("그런 게시판 있음: " +board.getText());
//        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("board", board);
        modelAndView.setViewName("board/list");
        return modelAndView;
    }
}
