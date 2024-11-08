package com.escass.advancedbbs.controllers;

import com.escass.advancedbbs.entities.CommentEntity;
import com.escass.advancedbbs.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @RequestMapping(value="/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postIndex(CommentEntity comment) {
        boolean result = this.commentService.writeComment(comment);
        JSONObject response = new JSONObject();
        response.put("result", result);
        return response.toString();
    }

    @RequestMapping(value="/comments", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public ResponseEntity<CommentEntity[]> getComments(@RequestParam(value = "articleIndex", required = false, defaultValue = "0") int articleIndex) {
//        this.commentService.getCommentsByArticleIndex(articleIndex);
        return null;
    }
}
