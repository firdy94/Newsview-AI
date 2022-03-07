package ibf2022.paf.newsserver2.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
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
				ps.setString(3, article.getId()); // TODO try string first
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

		final SqlRowSet rs = template.queryForRowSet(SQL.SQL_GET_USER_BY_NAME_AND_EMAIL);
		rs.next();
		int userCount = rs.getInt(1);

		if (userCount <= 0) {
			template.update(SQL.SQL_INSERT_USER, UUID.randomUUID().toString(), email, name, nickname);
		}
	}
}
