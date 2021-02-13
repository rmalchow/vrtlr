package de.vrtlr.impl.services;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

public class ChainedLocaleResolver implements LocaleResolver {

	private CookieLocaleResolver clr = new CookieLocaleResolver();
	private AcceptHeaderLocaleResolver ahlr = new AcceptHeaderLocaleResolver();
	
	public ChainedLocaleResolver() {
		clr.setDefaultLocale(new Locale("zz","ZZ"));
		clr.setCookieName("vrtlr_loc");
	}
	
	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		if(clr.resolveLocale(request)!=null && !clr.resolveLocale(request).getCountry().equalsIgnoreCase("zz")) {
			return clr.resolveLocale(request);
		}
		if(ahlr.resolveLocale(request)!=null) {
			return ahlr.resolveLocale(request);
		}
		return new Locale("en","US");
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		clr.setLocale(request, response, locale);
	}

}
