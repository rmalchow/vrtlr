package de.vrtlr.impl.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
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
	
	public static final byte[] favicon = Base64.decodeBase64("iVBORw0KGgoAAAANSUhEUgAAADkAAAA5CAYAAACMGIOFAAAACXBIWXMAAA7DAAAOwwHHb6hkAAAAGXRFWHRTb2Z0d2FyZQB3d3cuaW5rc2NhcGUub3Jnm+48GgAABDhJREFUaIHt2VuIVlUUwPGfo5M6WmZoZZlZEV0ossKie1ZYGlKkXeyK0YVKooceordAooslFZVU3rqJokVZll2F6MFIQeqpgpJ6yIQkqUlTxx7W/jyHk+b4nfPlDJ0/DLP23ufsb+1vrb3X2uujpqampqampqYkbRixr5X4N/qUfH8QnsOvGIYdeA8LS87bYxiJJTgxtY/BW3hHWLfXM0ZY6+DUPhdLMRx3pXav5jDhng/hMkzHM2hP44fgyX2j2q7p18Q7P2M5XsGpmIDLc+PrhYXb0NWkXgdgBjZjDVbh+ybnappZwjUJqw0vjN+Ds5ucezQW42gMxv3YoPwhudecjzty8u2F8RGY2cS8Z4m9PjS1rxandTNzlaYNL+fk+bt45jV79+1fgzexX3rvAWHFGTi2WUUbCjZDFzaK2NiF33BQ4ZlVOLOb8/UXVluL57ECX+Fp4b7fNqlnacbhtiRfhGmF8cPxeDfnugGXJHmI7HS+FleV0LE0bZiX5L6Yu4tnFumey86TedW9OD3Jr8tCU9OUyUy68Ltw0+3olB0YDVZh7B7mOQFfy8LNGKzG8fgGW0voWAkXy9x0PG4sjB+Bx/Ywx0xZCLoQtyb5CRxZXsXyOeZKXJDkT3Jygx8xyu5ddqA4dDak9nXCxQfiQKwrqR/KL3I7/hAuu0241v6FZ1bL9liR60XgJ6y5Kc03VSy2xzAetyR5ojgR84zGI7t5d4HMyg/ipCQvVOFNpoqJ8m76oQgneX4QKVrRZU/Bl+IO2objxAE0RuSrzea9/6CKRW4TifQQ4a5d4jKdZw1OK/RNw6tJnigSACL2LqhAr51U5RLLMCnJy3FpYXwxpuTajX27Mf2fgjfEF9UPv1SkV6W0y5KB/iI1K7I0J9+NM5I8Cg8neTrOqVq5qiy5FX+Je+AWsf8GFJ5ZI+6fRILwRZLvlGVO5+HzinTaSd8K59qBk8Xh0Y6jRMbSYL0IGZvFl7Ja3DhuwhyxwE5xGPVY2vFSkgeIkkiRJXhWWJwI/o0E/IVcf6VUWVXbKpKDwcJa7cJSedaK03dTak/C2yIR+DPXXylVlw7fldV7Pha5bZ5O4ZpEYv6dCEHTZPuyx9Nf5rIdeKowPicnzxIJeB8Vx8UiVVtyiziABgmrDZBVBMfhsyR3iGvZOhFTP6pYj5ZzpSzwT5Xd+GeLxRFZzcQkz8/1t4RWlPPfl2U8y8QeHSbqQJ2pf4JI40aKOm6nFtKKRW5O8w4SlYNBomTZqO6NFTFye+rfVdmkVzBZFv9uxge5sdk4VOzV+f+tWtXSgReTPFR2/RoqW9jk9NermSPKGHnuE6cs4b6lK3HdoZW/I64QVYMGfUShaqVY7Kd6QCWuLB1i/zUYJ8r+j+KKfaJRi5gru3ItEhfqYoWg5bT657CxIi9tF9ewKfipxZ9ZU1NTU1NTU/M/5G/LTr7EjSziiwAAAABJRU5ErkJggg=="); 
	
	
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
			
			out.addObject("mnemonic", s.getMnemonic());
			out.addObject("target", u);
			out.addObject("link", s);
			out.addObject("include", include);
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			
			Map<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			hints.put(EncodeHintType.MARGIN, 0);
			BitMatrix matrix = new MultiFormatWriter().encode(u, BarcodeFormat.QR_CODE, 240, 240, hints);
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

	@GetMapping(value = "/favicon.ico")
	public void shorten(HttpServletResponse response) throws SqlException, WriterException, IOException {
		response.setContentType("image/x-icon");
		response.getOutputStream().write(favicon);
	}
	
	
	@GetMapping(value = "/{shortened}")
	public ModelAndView shorten(@PathVariable String shortened, HttpServletResponse response) throws SqlException, WriterException, IOException {
		
		if(shortened.length()>20) return null;
		
		Shrtnd s = service.get(shortened);
		if(s==null) {
			response.sendRedirect(host);
			return null;
		}
		
		ModelAndView out = new ModelAndView("longer");
		out.addObject("link", s);
		out.addObject("meta", "10; url = "+s.getUrl());
		out.addObject("include", include);

		return out;
	}
	
}
