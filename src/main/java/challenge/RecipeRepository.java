package challenge;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages="challenge")
public interface RecipeRepository extends MongoRepository<Recipe, String> {
	
}