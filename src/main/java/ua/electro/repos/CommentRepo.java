package ua.electro.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.electro.models.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
