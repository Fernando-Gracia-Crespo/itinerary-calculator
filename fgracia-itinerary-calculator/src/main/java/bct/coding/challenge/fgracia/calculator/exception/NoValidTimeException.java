package bct.coding.challenge.fgracia.calculator.exception;

import bct.coding.challenge.fgracia.calculator.dto.ItineraryDTO;

public class NoValidTimeException extends Exception {

	private static final long serialVersionUID = 1L;
	
	ItineraryDTO itineraryDTO;

	public NoValidTimeException(ItineraryDTO itineraryDTO) {
		super();
		this.itineraryDTO = itineraryDTO;
	}
	
	
	public ItineraryDTO getItineraryDTO() {
		return itineraryDTO;
	}
}
