package challenge;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipeCommentRepository extends MongoRepository<RecipeComment, String> {

}