package bct.coding.challenge.fgracia.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import bct.coding.challenge.fgracia.api.dto.CityDTO;
import bct.coding.challenge.fgracia.api.entity.City;
import bct.coding.challenge.fgracia.api.exception.ResourceNotFoundException;
import bct.coding.challenge.fgracia.api.mapper.CityMapper;
import bct.coding.challenge.fgracia.api.repository.CityRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api (value = "Itinerary controller", description = "Itinerary methods")
public class CityController {
	
	@Autowired
	CityRepository cityRepository;

	@ApiOperation(value = "Method that returns all cities")
	@GetMapping("api/city")
	public List<CityDTO> getAll() {
		List<City> entities = (List<City>) cityRepository.findAll();
		return entities.stream().map(CityMapper::entityToDTO).collect(Collectors.toList());
	}
	
	@ApiOperation(value = "Method that returns one city")
	@GetMapping("api/city/{city_id}")
	public CityDTO getOne(
			@ApiParam(name="city_id",value = "City", type = "integer") @PathVariable(name="city_id") Integer cityId) throws Exception{
		Optional<City> city = cityRepository.findById(cityId);
		if(!city.isPresent()) {
			throw new ResourceNotFoundException("City not found: "+cityId);
		}
		return CityMapper.entityToDTO(city.get());
	}

}
