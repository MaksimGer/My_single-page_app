package org.example.services.comment;

import org.example.domain.entityes.Subject;
import org.example.domain.resources.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findAllByProduct(Subject subject);
    boolean save(Comment comment);
}
