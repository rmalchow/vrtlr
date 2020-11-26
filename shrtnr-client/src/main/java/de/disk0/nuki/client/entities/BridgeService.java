package de.disk0.shrtnr.client.entities;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class BridgeService {

	private static Log log = LogFactory.getLog(BridgeService.class);
	
	@Autowired
	private Bridge bridge;

	@PostConstruct
	public void init() {
		try {
			log.info(new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(bridge));
		} catch (Exception e) {
		}
	}
	
	public List<Lock> listLocks() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		URIBuilder ub = new URIBuilder();
		ub.setScheme("http")
		.setHost(bridge.getHost())
		.setPort(bridge.getPort())
		.setPath("/list")
		.addParameter("token", bridge.getToken());
		String url = ub.build().toString();
		
		log.info("URL: "+url);

		ResponseEntity<Lock[]> res = restTemplate.getForEntity(url, Lock[].class);
		
		return Arrays.asList(res.getBody());
		
	}
	
	public boolean unlock(String id, String pin) throws RestClientException, URISyntaxException, Exception {
		if(!pin.equalsIgnoreCase(bridge.getPin())) return false;
		for(Lock l : listLocks()) {
			if(l.getshrtnrId().compareTo(id)==0) {
				RestTemplate restTemplate = new RestTemplate();
				URIBuilder ub = new URIBuilder();
				ub.setScheme("http")
				.setHost(bridge.getHost())
				.setPort(bridge.getPort())
				.setPath("/unlock")
				.addParameter("token", bridge.getToken())
				.addParameter("deviceType", l.getDeviceType()+"")
				.addParameter("shrtnrId", id);
				
				String url = ub.build().toString();
				
				log.info("URL: "+url);
				
				ResponseEntity<LockResponse> res = restTemplate.getForEntity(url, LockResponse.class);
				
				return res.getBody().isSuccess();
				
			}
		}
		
		return false;
	}
	
	
}
