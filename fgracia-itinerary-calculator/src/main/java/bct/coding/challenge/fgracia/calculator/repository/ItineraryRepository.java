package bct.coding.challenge.fgracia.calculator.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import bct.coding.challenge.fgracia.calculator.dto.ItineraryDTO;
import bct.coding.challenge.fgracia.calculator.exception.APIAccessException;

@Component
public class ItineraryRepository {
	
	@Value("${itinerary.api.url}")
	private String apiURL;
	
	private RestTemplate restTemplate = new RestTemplate();

	public ItineraryDTO[] getItinerariesFrom(String city) throws APIAccessException{
		ResponseEntity<ItineraryDTO[]> response;
		try {
			response = restTemplate.getForEntity(apiURL+"/itinerary/"+city.toUpperCase(), ItineraryDTO[].class);
		} catch (Exception e) {
			throw new APIAccessException("Error accessing the data",e);
		}
		if(response==null || response.getStatusCode()!=HttpStatus.OK) {
			throw new APIAccessException("Error accessing the data");
		}
		return response.getBody();
	}
	
}
