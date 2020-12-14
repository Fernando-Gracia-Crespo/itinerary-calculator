package bct.coding.challenge.fgracia.calculator.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import bct.coding.challenge.fgracia.calculator.auth.Credentials;
import bct.coding.challenge.fgracia.calculator.dto.CityDTO;
import bct.coding.challenge.fgracia.calculator.exception.APIAccessException;

@Component
public class CityRepository {
	
	@Value("${itinerary.api.url}")
	private String apiURL;
	
	private RestTemplate restTemplate = new RestTemplate();

	public CityDTO[] getAllCities(Credentials credentials) throws APIAccessException{
		ResponseEntity<CityDTO[]> response;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setBearerAuth(credentials.getToken());
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			response = restTemplate.exchange(apiURL+"/city", HttpMethod.GET,  entity, CityDTO[].class);
		} catch (Exception e) {
			throw new APIAccessException("Error accessing the data",e);
		}
		if(response==null || response.getStatusCode()!=HttpStatus.OK) {
			throw new APIAccessException("Error accessing the data");
		}
		return response.getBody();
	}
	
	public boolean cityExists(Credentials credentials, Integer city) throws APIAccessException{
		ResponseEntity<CityDTO> response = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setBearerAuth(credentials.getToken());
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			response = restTemplate.exchange(apiURL+"/city/"+city, HttpMethod.GET,  entity, CityDTO.class);
		} catch (HttpClientErrorException.NotFound e) {
			return false;
		} catch (Exception e) {
			throw new APIAccessException("Error accessing the data",e);
		}
		if(response==null || response.getStatusCode()!=HttpStatus.OK) {
			throw new APIAccessException("Error accessing the data");
		}
		return true;
	}
	
}
