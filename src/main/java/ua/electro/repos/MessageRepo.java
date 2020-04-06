package ua.electro.repos;

import org.springframework.data.repository.CrudRepository;
import ua.electro.models.Message;

import java.util.List;

public interface MessageRepo extends CrudRepository<Message, Long> {

    List<Message> findByTag(String tag);

}
