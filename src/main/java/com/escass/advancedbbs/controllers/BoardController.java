package com.escass.advancedbbs.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value ="/board")
public class BoardController {
    @RequestMapping(value ="/list", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getList(@RequestParam(value = "id", required = false) String id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("board/list");
        return modelAndView;
    }
}
