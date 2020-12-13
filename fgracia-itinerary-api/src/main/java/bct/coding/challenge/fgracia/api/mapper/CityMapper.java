package bct.coding.challenge.fgracia.api.mapper;

import bct.coding.challenge.fgracia.api.dto.CityDTO;
import bct.coding.challenge.fgracia.api.entity.City;

public class CityMapper {

	public static CityDTO entityToDTO(City entity) {
		CityDTO dto = new CityDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}
	
	public static City dtoToEntity(CityDTO dto) {
		City entity = new City();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		return entity;
	}
	
}
