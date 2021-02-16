package de.vrtlr.impl.services;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.disk0.dbutil.api.exceptions.SqlException;
import de.vrtlr.api.entities.IdGeneratorFastShort;
import de.vrtlr.api.entities.Shrtnd;
import de.vrtlr.impl.repos.ShrtnrRepo;

@Component
public class ShrtnrService {

	@Autowired
	private ShrtnrRepo r;
	
	private IdGeneratorFastShort idg = new IdGeneratorFastShort();
	
	private String getMetaTagContent(Document document, String cssQuery) {
		Element elm = document.select(cssQuery).first();
		if (elm != null) {
			return elm.attr("content");
		}
		return "";
	}

	public Shrtnd create(String url) throws SqlException {

		try {
			
			List<Shrtnd> se = r.find(null, url, 0, 1);
			if(se.size()>0) return se.get(0);
			
			if(url.toUpperCase().startsWith("HTTP://")) {
				// ok
			} else if(url.toUpperCase().startsWith("HTTPS://")) {
				// ok
			} else {
				url = "https://"+url;
			}
			
			
			Document document = Jsoup.connect(url).get();
			String title = getMetaTagContent(document, "meta[name=title]");
			String desc = getMetaTagContent(document, "meta[name=description]");
			String ogTitle = getMetaTagContent(document, "meta[property=og:title]");
			String ogDesc = getMetaTagContent(document, "meta[property=og:description]");
			String ogImage = getMetaTagContent(document, "meta[property=og:image]");

			Shrtnd s = new Shrtnd();
			s.setUrl(url);
			s.setTitle(StringUtils.defaultString(ogTitle, title));
			s.setDesc(StringUtils.defaultString(ogDesc, desc));
			s.setImage(ogImage);
			
			return r.save(s,idg.generateId());
		} catch (Exception e) {
		}
		throw new RuntimeException("invalid URL");
	}

	public Shrtnd get(String id) throws SqlException {
		return r.get(id);
	}
	
}
