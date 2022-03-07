package ibf2022.paf.newsserver2.repositories;

public class SQL {

	static final String SQL_INSERT_ARTICLES = "insert ignore into articles(author,description, id, image_path, published_date, title, url, website) values(?,?,?,?,?,?,?,?)";
	static final String SQL_GET_ARTICLE_BY_ID_AND_URL = " select * from articles where id = ?  && url=? ";
	static final String SQL_DELETE_OLD_ARTICLES = "delete from articles WHERE url != 'dkhfksdfuhduciuduihhfsufh' and uploaded_at < (NOW() - INTERVAL 5 MINUTE)";
	static final String SQL_INSERT_USER = "insert into userinfo(id,email, name, nickname, url) values(?,?,?,?)";
	// static final String SQL_GET_USER_BY_NAME_EMAIL = "select * from userinfo
	// where name = ? && email=? ";
	static final String SQL_GET_USER_BY_NAME_AND_EMAIL = " select count(name) from userinfo where name = ?  && email=? ";
	static final String SQL_INSERT_FAV_ARTICLES = "insert into favarticles(fav_mail,fav_id) values(?,?)";
	static final String SQL_GET_ARTICLE_BY_ID = " select * from articles where id = ?";
	static final String SQL_GET_FAV_ARTICLE_BY_ID_AND_URL = " select fav_id from favarticles where fav_id = ?";
	static final String SQL_DELETE_FAV_ARTICLE_BY_ID = "delete from favarticles where fav_id=?";

}
