package challenge;

import java.util.List;
import java.util.Optional;

public interface RecipeService {

	Recipe save(Recipe recipe);

	void update(String id, Recipe recipe);

	void delete(String id);

	Optional<Recipe> get(String id);

	List<Recipe> listByIngredient(String ingredient);

	List<Recipe> search(String search);

	void like(String id, String userId);

	void unlike(String id, String userId);

	RecipeComment addComment(String id, RecipeComment comment);

	void updateComment(String id, String commentId, RecipeComment comment);

	void deleteComment(String id, String commentId);
	
}