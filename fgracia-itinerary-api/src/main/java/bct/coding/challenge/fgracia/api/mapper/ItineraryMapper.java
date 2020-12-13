package bct.coding.challenge.fgracia.api.mapper;

import bct.coding.challenge.fgracia.api.dto.ItineraryDTO;
import bct.coding.challenge.fgracia.api.entity.Itinerary;

public class ItineraryMapper {

	public static ItineraryDTO entityToDTO(Itinerary entity) {
		ItineraryDTO dto = new ItineraryDTO();
		dto.setId(entity.getId());
		dto.setOriginCity(CityMapper.entityToDTO(entity.getOriginCity()));
		dto.setDestinyCity(CityMapper.entityToDTO(entity.getDestinyCity()));
		dto.setDepartureTime(entity.getDepartureTime());
		dto.setArrivalTime(entity.getArrivalTime());
		return dto;
	}
	
	public static Itinerary dtoToEntity(ItineraryDTO dto) {
		Itinerary entity = new Itinerary();
		entity.setId(dto.getId());
		entity.setOriginCity(CityMapper.dtoToEntity(dto.getOriginCity()));
		entity.setDestinyCity(CityMapper.dtoToEntity(dto.getDestinyCity()));
		entity.setDepartureTime(dto.getDepartureTime());
		entity.setArrivalTime(dto.getArrivalTime());
		return entity;
	}
	
}
