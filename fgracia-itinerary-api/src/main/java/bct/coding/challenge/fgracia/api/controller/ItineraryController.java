package bct.coding.challenge.fgracia.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import bct.coding.challenge.fgracia.api.dto.ItineraryDTO;
import bct.coding.challenge.fgracia.api.entity.Itinerary;
import bct.coding.challenge.fgracia.api.mapper.ItineraryMapper;
import bct.coding.challenge.fgracia.api.repository.ItineraryRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api (value = "Itinerary controller", description = "Itinerary methods")
public class ItineraryController {
	
	@Autowired
	ItineraryRepository itineraryRepository;

	@ApiOperation(value = "Method that returns all city conexions")
	@GetMapping("api/itinerary")
	public List<ItineraryDTO> getAll() {
		List<Itinerary> entities = (List<Itinerary>) itineraryRepository.findAll();
		return entities.stream().map(ItineraryMapper::entityToDTO).collect(Collectors.toList());
	}
	
	@ApiOperation(value = "Method that returns all conexions from an origin city")
	@GetMapping("api/itinerary/{city}")
	public List<ItineraryDTO> getItinerariesFromCity(
			@ApiParam(name="city",value = "City of origin", type = "string") @PathVariable String city){
		return itineraryRepository.findAllByOriginCity(city).stream().map(ItineraryMapper::entityToDTO).collect(Collectors.toList());
	}

}
