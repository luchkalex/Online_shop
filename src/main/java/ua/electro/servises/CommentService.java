package ua.electro.servises;


import org.springframework.stereotype.Service;
import ua.electro.models.Comment;
import ua.electro.repos.CommentRepo;

@Service
public class CommentService {

    private final CommentRepo commentRepo;

    public CommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }


    public void save(Comment comment) {
        commentRepo.save(comment);
    }
}
