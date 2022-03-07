package ibf2022.paf.newsserver2.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ibf2022.paf.newsserver2.models.Article;

@Repository
public class NewsviewRepository {

	@Autowired
	private JdbcTemplate template;

	@Transactional
	public void addArticles(List<Article> articles) {
		int rowsDeleted = template.update(SQL.SQL_DELETE_OLD_ARTICLES);

		template.batchUpdate(SQL.SQL_INSERT_ARTICLES, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Article article = articles.get(i);
				ps.setString(1, article.getAuthor());
				ps.setString(2, article.getDescription());
				ps.setString(3, article.getId());
				ps.setString(4, article.getImagePath());
				ps.setString(5, article.getPublishedDate());
				ps.setString(6, article.getTitle());
				ps.setString(7, article.getUrlPath());
				ps.setString(8, article.getWebsite());

			}

			@Override
			public int getBatchSize() {
				return articles.size();
			}
		});

	}

	@Transactional
	public void setUser(String name, String email, String nickname) {

		final SqlRowSet rs = template.queryForRowSet(SQL.SQL_GET_USER_COUNT_BY_NAME_AND_EMAIL, name, email);
		rs.next();
		int userCount = rs.getInt(1);

		if (userCount <= 0) {
			template.update(SQL.SQL_INSERT_USER, UUID.randomUUID().toString(), email, name, nickname);
		}
	}

	@Transactional
	public Optional<Article> getArticleById(String id) {
		final SqlRowSet rs = template.queryForRowSet(SQL.SQL_GET_ARTICLE_COUNT_BY_ID, id);
		rs.next();
		int articleCount = rs.getInt(1);

		if (articleCount <= 0) {
			return Optional.empty();
		}
		final SqlRowSet rs1 = template.queryForRowSet(SQL.SQL_GET_ARTICLE_BY_ID, id);
		rs.next();
		Article article = new Article();
		article.setAuthor(rs.getString("author"));
		article.setDescription(rs.getString("description"));
		article.setId(id);
		article.setImagePath(rs.getString("image_path"));
		article.setPublishedDate(rs.getString("uploaded_at"));
		article.setTitle(rs.getString("title"));
		article.setUrlPath(rs.getString("url"));
		article.setWebsite(rs.getString("website"));

		return Optional.ofNullable(article);
	}

	public Optional<List<String>> getFavArticleIds(String email) {
		final SqlRowSet rs = template.queryForRowSet(SQL.SQL_GET_FAV_ARTICLE_IDS_BY_EMAIL, email);
		List<String> ids = new ArrayList<>();
		while (rs.next()) {
			ids.add(rs.getString("id"));
		}
		if (ids.isEmpty()) {
			return Optional.empty();
		}
		return Optional.ofNullable(ids);
	}

	public void deleteFavArticle(String id, String email) {
		int rowsDeleted = template.update(SQL.SQL_DELETE_FAV_ARTICLE_BY_ID_AND_EMAIL, id, email);
	}

	public void addFavArticle(String id, String email) {
		int rowsAdded = template.update(SQL.SQL_INSERT_FAV_ARTICLE, email, id);
	}

}
