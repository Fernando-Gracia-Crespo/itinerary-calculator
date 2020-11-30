package bct.coding.challenge.fgracia.api.mapper;

import bct.coding.challenge.fgracia.api.dto.ItineraryDTO;
import bct.coding.challenge.fgracia.api.entity.Itinerary;

public class ItineraryMapper {

	public static ItineraryDTO entityToDTO(Itinerary entity) {
		ItineraryDTO dto = new ItineraryDTO();
		dto.setId(entity.getId());
		dto.setOriginCity(entity.getOriginCity());
		dto.setDestinyCity(entity.getDestinyCity());
		dto.setDepartureTime(entity.getDepartureTime());
		dto.setArrivalTime(entity.getArrivalTime());
		return dto;
	}
	
	public static Itinerary dtoToEntity(ItineraryDTO dto) {
		Itinerary entity = new Itinerary();
		entity.setId(dto.getId());
		entity.setOriginCity(dto.getOriginCity());
		entity.setDestinyCity(dto.getDestinyCity());
		entity.setDepartureTime(dto.getDepartureTime());
		entity.setArrivalTime(dto.getArrivalTime());
		return entity;
	}
	
}
