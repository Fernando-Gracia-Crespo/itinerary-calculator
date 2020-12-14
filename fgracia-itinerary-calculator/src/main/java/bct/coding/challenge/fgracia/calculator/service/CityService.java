package bct.coding.challenge.fgracia.calculator.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import bct.coding.challenge.fgracia.calculator.auth.Credentials;
import bct.coding.challenge.fgracia.calculator.dto.CityDTO;
import bct.coding.challenge.fgracia.calculator.repository.CityRepository;

@Service
public class CityService {
	
	public enum CalculateMode { CONNECTIONS, TIME };
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	public LoginService loginService;
	
	@Value("${itinerary.api.user}")
	private String apiUser;

	@Value("${itinerary.api.pass}")
	private String apiPass;
	
	public List<CityDTO> getAllCities() throws Exception{
		Credentials credentials = loginService.getUserToken(apiUser, apiPass);
		return Arrays.asList(cityRepository.getAllCities(credentials));
	}	
	
}
