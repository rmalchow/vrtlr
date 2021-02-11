package de.vrtlr.impl.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import de.disk0.dbutil.api.exceptions.SqlException;
import de.vrtlr.api.entities.Shrtnd;
import de.vrtlr.impl.services.ShrtnrService;

@Controller
@RequestMapping(value = "")
public class VrtlrController {
	
	@Autowired
	private ShrtnrService service;
	
	@Value(value = "${host:http://127.0.0.1:8080/}")
	private String host;
	
	@Value(value = "${include:}")
	private String include;
	
	
	@GetMapping(value = "/")
	public ModelAndView overview() {
		ModelAndView out = new ModelAndView("index");
		System.err.println("controller!");
		out.addObject("include", include);
		return out;
	}
	
	@PostMapping(value = "/shrtn")
	public ModelAndView shorten(@RequestParam String url, HttpServletRequest req) throws SqlException, WriterException, IOException {
		ModelAndView out = new ModelAndView("index");
		out.addObject("url",url);
		out.addObject("hasError",false);

		url = StringUtils.defaultString(url,"").trim();
		
		if(StringUtils.isEmpty(url)) {
			return out;
		}
		
		if(url.length()>1024) {
			out.addObject("hasError",true);
			out.addObject("error","URL too long");
			return out;
		}

		
		try {
			Shrtnd s = service.create(url);

			String u = host+s.getId();
			
			out.addObject("target", u);
			out.addObject("link", s);
			out.addObject("include", include);
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			
			Map<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			hints.put(EncodeHintType.MARGIN, 0);
			BitMatrix matrix = new MultiFormatWriter().encode(u, BarcodeFormat.QR_CODE, 120, 120, hints);
			MatrixToImageWriter.writeToStream(matrix, "png", os);
			out.addObject("qr", "data:image/png;base64,"+Base64Utils.encodeToString(os.toByteArray()));
			out.setViewName("shortened");
			
			return out;
			
		} catch (Exception e) {
			out.addObject("hasError",true);
			out.addObject("error",e.getMessage());
		}
		return out;
	}
	
	@GetMapping(value = "/{shortened}")
	public ModelAndView shorten(@PathVariable String shortened, HttpServletResponse response) throws SqlException, WriterException, IOException {
		
		if(shortened.length()>20) return null;
		
		Shrtnd s = service.get(shortened);
		if(s==null) {
			response.sendRedirect("/");
			return null;
		}
		
		ModelAndView out = new ModelAndView("longer");
		out.addObject("link", s);
		out.addObject("meta", "10; url = "+s.getUrl());
		out.addObject("include", include);

		return out;
	}
	
}
