package bct.coding.challenge.fgracia.calculator.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import bct.coding.challenge.fgracia.calculator.auth.Credentials;
import bct.coding.challenge.fgracia.calculator.exception.APIAccessException;

@Service
public class LoginService {

	@Value("${itinerary.api.url}")
	private String apiURL;
	
	private RestTemplate restTemplate = new RestTemplate();

	public Credentials getUserToken(String user, String password) throws APIAccessException{
		Credentials credentials = new Credentials();
		credentials.setUser(user);
		credentials.setPassword(password);
		ResponseEntity<String> response;
		try {
			response = restTemplate.postForEntity(apiURL+"/user?user="+user+"&password="+password, null, String.class);
		} catch (Exception e) {
			throw new APIAccessException("Error accessing the data",e);
		}
		if(response==null || response.getStatusCode()!=HttpStatus.OK) {
			throw new APIAccessException("Error accessing the data");
		}
		String token = response.getBody().substring("Bearer ".length());
		credentials.setToken(token);
		return credentials;
	}
	
	
	

}
