package bct.coding.challenge.fgracia.api.dto;

import lombok.Data;

@Data
public class ItineraryDTO {
	
	private Integer id;
	private CityDTO originCity;
	private CityDTO destinyCity;
	private String departureTime;
	private String arrivalTime;
	
}
