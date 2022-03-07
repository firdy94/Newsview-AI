package ibf2022.paf.newsserver2.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.tone_analyzer.v3.model.ToneOptions;

import ibf2022.paf.newsserver2.models.TonesAnalysis;
import ibf2022.paf.newsserver2.models.submodels.tonesubmodels.DocumentTone;
import ibf2022.paf.newsserver2.models.submodels.tonesubmodels.SentenceTone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToneAnalyzeService {
	@Autowired
	ArticleService articleSvc;

	public Optional<ToneAnalysis> getTone(String url) {
		String text = "";
		try {
			if (articleSvc.getArticleBody(url).isPresent()) {
				text = articleSvc.getArticleBody(url).get();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
		IamAuthenticator authenticator = new IamAuthenticator.Builder()
				.apikey(System.getenv("ibmtoneapikey"))
				.build();
		ToneAnalyzer toneAnalyzer = new ToneAnalyzer(System.getenv("ibmtoneversion"), authenticator);
		toneAnalyzer.setServiceUrl(System.getenv("ibmtoneurl"));
		ToneOptions toneOptions = new ToneOptions.Builder().text(text).build();
		return Optional.ofNullable(toneAnalyzer.tone(toneOptions).execute().getResult());

	}

	public Optional<TonesAnalysis> parseToneAnalysis(ToneAnalysis toneResults) {
		Optional<DocumentTone> docTone = Optional
				.ofNullable(new DocumentTone(toneResults.getDocumentTone().getTones()));

		TonesAnalysis tAnalysis = new TonesAnalysis();
		if (docTone.isPresent()) {
			tAnalysis.setDocumentTone(docTone.get());
		}
		if (toneResults.getSentencesTone() != null) {
			Optional<List<SentenceTone>> senTone = Optional.ofNullable(toneResults.getSentencesTone().stream()
					.filter(p -> (p.getToneCategories() != null && (p.getTones() == null)))
					.map(p -> new SentenceTone(p.getText(), p.getTones()))
					.collect(Collectors.toList()));
			if (senTone.isPresent()) {
				tAnalysis.setSentenceTones(senTone.get());
			}
		}
		return Optional.ofNullable(tAnalysis);
	}
}
