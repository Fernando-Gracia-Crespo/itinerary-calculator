package bct.coding.challenge.fgracia.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bct.coding.challenge.fgracia.api.dto.ItineraryDTO;
import bct.coding.challenge.fgracia.api.entity.Itinerary;
import bct.coding.challenge.fgracia.api.exception.ResourceNotFoundException;
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

	@ApiOperation(value = "Method that returns all city conexions (with optional filters)")
	@GetMapping("api/itinerary")
	public List<ItineraryDTO> getAll(
			@ApiParam(name="from", value = "City of origin",type = "integer",required=false) @RequestParam(required=false) Optional<Integer> from,
			@ApiParam(name="to", value = "City of destination",type = "integer",required=false) @RequestParam(required=false) Optional<Integer> to
			) {
		List<Itinerary> entities;
		if (from.isPresent() && to.isPresent()) {
			entities = (List<Itinerary>) itineraryRepository.findAllByOriginCityIdAndDestinyCityId(from.get(), to.get());
		} else if(from.isPresent()){
			entities = (List<Itinerary>) itineraryRepository.findAllByOriginCityId(from.get());
		} else if(to.isPresent()) {
			entities = (List<Itinerary>) itineraryRepository.findAllByDestinyCityId(to.get());
		} else {
			entities = (List<Itinerary>) itineraryRepository.findAll();
		}
		return entities.stream().map(ItineraryMapper::entityToDTO).collect(Collectors.toList());
	}
	
	@ApiOperation(value = "Method that returns one city conection")
	@GetMapping("api/itinerary/{itinerary_id}")
	public ItineraryDTO getItinerary(
			@ApiParam(name="itinerary_id",value = "Itinerary id", type = "Integer") @PathVariable(name="itinerary_id") Integer id){
		Optional<Itinerary> itinerary = itineraryRepository.findById(id);
		if(!itinerary.isPresent()) {
			throw new ResourceNotFoundException("Itinerary not found: "+id);
		}
		return ItineraryMapper.entityToDTO(itinerary.get());
	}

}
