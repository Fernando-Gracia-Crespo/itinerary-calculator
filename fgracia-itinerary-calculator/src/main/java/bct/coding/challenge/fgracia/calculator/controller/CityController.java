package bct.coding.challenge.fgracia.calculator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import bct.coding.challenge.fgracia.calculator.dto.CityDTO;
import bct.coding.challenge.fgracia.calculator.service.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "City controller", description = "City methods")
public class CityController {
	
	@Autowired
	public CityService cityService;
	
	@ApiOperation(value = "Method that returns all the cities")
	@GetMapping("/itinary-calculator/cities")
	public List<CityDTO> getAllCities() throws Exception{
		return cityService.getAllCities();
	}

}
