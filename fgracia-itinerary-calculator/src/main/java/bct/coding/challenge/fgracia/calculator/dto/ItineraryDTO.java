package bct.coding.challenge.fgracia.calculator.dto;

public class ItineraryDTO {
	
	private Integer id;
	private CityDTO originCity;
	private CityDTO destinyCity;
	private String departureTime;
	private String arrivalTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public CityDTO getOriginCity() {
		return originCity;
	}
	public void setOriginCity(CityDTO originCity) {
		this.originCity = originCity;
	}
	public CityDTO getDestinyCity() {
		return destinyCity;
	}
	public void setDestinyCity(CityDTO destinyCity) {
		this.destinyCity = destinyCity;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	

}
