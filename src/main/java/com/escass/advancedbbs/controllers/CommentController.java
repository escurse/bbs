package com.escass.advancedbbs.controllers;

import com.escass.advancedbbs.entities.CommentEntity;
import com.escass.advancedbbs.results.comment.DeleteCommentResult;
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

    @RequestMapping(value="/", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteIndex(@RequestParam(value="index", required = false, defaultValue = "0") int index,
                              @RequestParam(value="password", required = false) String password) {
        // 전달받은 index와 password를 통해 댓글을 삭제 처리할 수 있는 MVC 패턴을 완성해 보세요.
        // 단, 실제로 DELETE 쿼리를 실행하지 말고, `deleted_at` 열 값을 삭제 일시로 지정하여 수정하도록 하세요.
        DeleteCommentResult result = this.commentService.deleteArticle(index, password);
        JSONObject response = new JSONObject();
        response.put("result", result.name().toLowerCase());
        return response.toString();
    }

    @RequestMapping(value="/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postIndex(CommentEntity comment) {
        boolean result = this.commentService.writeComment(comment);
        JSONObject response = new JSONObject();
        response.put("result", result);
        return response.toString();
    }

    @RequestMapping(value="/comments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CommentEntity[]> getComments(@RequestParam(value = "articleIndex", required = false, defaultValue = "0") int articleIndex) {
        CommentEntity[] comments = this.commentService.getCommentsByArticleIndex(articleIndex);
        if (comments == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(comments);
    }
}
