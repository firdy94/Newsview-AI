package ibf2022.paf.newsserver2.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ibf2022.paf.newsserver2.models.Article;

public interface NewsviewMongoRepository extends MongoRepository<Article, String> {

	@Query("{id:'?0'}")
	Optional<Article> findItemByUuid(String id);

	// @Query()
	// @Query(value = "{category:'?0'}", fields = "{'name' : 1, 'quantity' : 1}")
	// List<Article> findAll(String category);

}
