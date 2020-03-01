package org.example.services.comment;

import org.example.domain.entityes.*;
import org.example.domain.resources.Comment;
import org.example.repos.UserRepo;
import org.example.services.attribute.AttributeService;
import org.example.services.category.CategoryService;
import org.example.services.params.ParamsService;
import org.example.services.subject.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ParamsService paramsService;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryService categoryService;

    public List<Comment> findAllByProduct(Subject product) {
        Attribute author = attributeService.findById(1l);
        Attribute text = attributeService.findById(2l);

        List<Subject> productComments = subjectService.findAllByParent(product);
        List<Comment> comments = new ArrayList<>();

        for (Subject productComment: productComments){

            String authorIdStr = paramsService.findByProductAndAttribute(productComment, author).getValue();
            String commentText = paramsService.findByProductAndAttribute(productComment, text).getValue();
            Long authorId = Long.parseLong(authorIdStr);

            User commentAuthor = userRepo.findById(authorId).orElse(null);

            if(commentAuthor == null){
                break;
            }

            Comment comment = new Comment();
            comment.setProduct(product);
            comment.setAuthor(commentAuthor);
            comment.setText(commentText);

            comments.add(comment);
        }

        return comments;
    }

    @Override
    public boolean save(Comment comment) {
        Attribute author = attributeService.findById(1l);
        Attribute text = attributeService.findById(2l);
        Category categoryComment = categoryService.findById(5l);

        Subject newComment = new Subject();
        newComment.setParent(comment.getProduct());
        newComment.setCategory(categoryComment);
        subjectService.save(newComment);

        User user = comment.getAuthor();
        String textValue = comment.getText();

        Params newParamsAuthor = new Params(newComment, author, String.valueOf(user.getId()));
        paramsService.save(newParamsAuthor);

        Params newParamsText = new Params(newComment, text, textValue);
        paramsService.save(newParamsText);

        return true;
    }
}