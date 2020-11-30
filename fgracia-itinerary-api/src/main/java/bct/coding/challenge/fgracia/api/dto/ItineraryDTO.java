package bct.coding.challenge.fgracia.api.dto;

public class ItineraryDTO {
	
	private Integer id;
	private String originCity;
	private String destinyCity;
	private String departureTime;
	private String arrivalTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOriginCity() {
		return originCity;
	}
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}
	public String getDestinyCity() {
		return destinyCity;
	}
	public void setDestinyCity(String destinyCity) {
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
