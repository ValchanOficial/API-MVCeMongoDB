package challenge;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private RecipeRepository recipeRepository;
	
	@Autowired
	private RecipeCommentRepository recipeCommentRepository;

	@Override
	public Recipe save(Recipe recipe) {
		return recipeRepository.insert(recipe);
	}

	@Override
	public void update(String id, Recipe recipe) {
		mongoTemplate.updateFirst(
				Query.query(Criteria.where("_id").is(id)), Update.update("title", recipe.getTitle())
						.set("description", recipe.getDescription())
						.set("ingredients", recipe.getIngredients()), Recipe.class);
		}

	@Override
	public void delete(String id) {
		recipeRepository.deleteById(id);
	}

	@Override
	public Optional<Recipe> get(String id) {
		return recipeRepository.findById(id);
	}

	@Override
	public List<Recipe> listByIngredient(String ingredient) {
		return mongoTemplate.find(
				Query.query(Criteria.where("ingredients").is(ingredient)).with(Sort.by("title").ascending()), Recipe.class);
	}

	@Override
	public List<Recipe> search(String search) {
		return mongoTemplate.find(Query.query(new Criteria()
				.orOperator(Criteria.where("title").regex(search, "i"), Criteria.where("description").regex(search, "i")))
				.with(Sort.by("title").ascending()), Recipe.class);
	}

	@Override
	public void like(String id, String userId) {
		mongoTemplate.updateFirst(
				Query.query(Criteria.where("_id").is(id)), new Update().addToSet("likes", userId), Recipe.class);
	}

	@Override
	public void unlike(String id, String userId) {
		mongoTemplate.updateFirst( 
				Query.query(Criteria.where("_id").is(id)), new Update().pull("likes", userId), Recipe.class);
	}

	@Override
	public RecipeComment addComment(String id, RecipeComment comment) {
		comment.setId(new ObjectId().toString());
		recipeCommentRepository.save(comment);
		mongoTemplate.updateFirst(
				Query.query(Criteria.where("_id").is(id)), new Update().addToSet("comments", comment), Recipe.class);
		return comment;
	}

	@Override
	public void updateComment(String id, String commentId, RecipeComment comment) {
		mongoTemplate.updateFirst(
				Query.query(Criteria.where("_id").is(id).and("comments._id").is(commentId)), 
				Update.update("comments.$.comment", comment.getComment()), Recipe.class);
	}

	@Override
	public void deleteComment(String id, String commentId) {
		  mongoTemplate.updateFirst(
	                Query.query(Criteria.where("_id").is(id)),
	                new Update().pull("comments", Query.query(Criteria.where("_id").is(commentId))), Recipe.class
	        );
	}
}