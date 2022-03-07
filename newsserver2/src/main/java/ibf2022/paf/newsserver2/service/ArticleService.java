package ibf2022.paf.newsserver2.service;

import java.net.URL;
import java.util.Optional;

import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;

import org.springframework.stereotype.Service;

@Service
public class ArticleService {

	public Optional<String> getArticleBody(String url) throws Exception {
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		URL html;
		String body = "";
		html = new URL(url);
		ParseContext pcontext = new ParseContext();
		TikaInputStream tiks = TikaInputStream.get(html);
		HtmlParser htmlparser = new HtmlParser();
		htmlparser.parse(tiks, handler, metadata, pcontext);

		String[] metadataNames = handler.toString().strip().split("\n");

		for (String name : metadataNames) {
			if (name.length() > 120) {
				body += name;
			}
		}
		return Optional.ofNullable(body);
	}

}
