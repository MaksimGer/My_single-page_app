package org.example.controlers;

import org.example.domain.entityes.Subject;
import org.example.domain.entityes.User;
import org.example.domain.resources.Comment;
import org.example.domain.resources.CommentForm;
import org.example.services.comment.CommentService;
import org.example.services.subject.SubjectService;
import org.example.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/{productID}",
            method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Comment> getCommentsByProduct(@PathVariable("productID") Subject product){
        return commentService.findAllByProduct(product);
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            produces = { MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Comment addComment(@RequestBody CommentForm commentForm) {
        Long productId = commentForm.getProductId();
        Long userId = commentForm.getUserId();
        String text = commentForm.getText();

        Subject subject = subjectService.findById(productId);
        User user = userService.findById(userId);
        Comment comment = new Comment(user, subject, text);
        commentService.save(comment);

        return comment;
    }
}