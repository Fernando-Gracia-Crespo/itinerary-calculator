package bct.coding.challenge.fgracia.calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import bct.coding.challenge.fgracia.calculator.service.ItineraryService;
import bct.coding.challenge.fgracia.calculator.service.ItineraryService.CalculateMode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "Itinerary calculator controller", description = "Itinerary calculator methods")
public class ItineraryController {

	@Autowired
	public ItineraryService itineraryService;
	
	@ApiOperation(value = "Method that returns the shortest path between two cities in terms of number of connections")
	@GetMapping("/itinerary-calculator/{origin}/{destiny}/shorter-in-connections")
	public Object calculateShortInConnections(
			@ApiParam(name="origin", value = "City of origin", type = "string") @PathVariable String origin,
			@ApiParam(name="destiny", value = "City of destination", type = "string") @PathVariable String destiny) {
		try {
			return itineraryService.getShorterRoute(origin, destiny, CalculateMode.CONNECTIONS);
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@ApiOperation(value = "Method that returns the shortest path between two cities in terms of time")
	@GetMapping("/itinerary-calculator/{origin}/{destiny}/shorter-in-time")
	public Object calculateShortInTime(
			@ApiParam(name="origin", value = "City of origin", type = "string") @PathVariable String origin,
			@ApiParam(name="destiny", value = "City of destination", type = "string") @PathVariable String destiny) {
		try {
			return itineraryService.getShorterRoute(origin, destiny, CalculateMode.TIME);
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
}
