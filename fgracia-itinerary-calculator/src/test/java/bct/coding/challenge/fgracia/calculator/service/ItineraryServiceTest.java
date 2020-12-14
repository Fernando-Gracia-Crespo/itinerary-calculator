package bct.coding.challenge.fgracia.calculator.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import bct.coding.challenge.fgracia.calculator.auth.Credentials;
import bct.coding.challenge.fgracia.calculator.dto.CityDTO;
import bct.coding.challenge.fgracia.calculator.dto.ItineraryDTO;
import bct.coding.challenge.fgracia.calculator.repository.CityRepository;
import bct.coding.challenge.fgracia.calculator.repository.ItineraryRepository;
import bct.coding.challenge.fgracia.calculator.service.ItineraryService.CalculateMode;

@SpringBootTest
public class ItineraryServiceTest {

	@Mock
	private ItineraryRepository itineraryRepository;
	
	@Mock
	private LoginService loginService;
	
	@Mock
	private CityRepository cityRepository;
	
	@InjectMocks
	private ItineraryService itineraryService;
	
	@org.junit.jupiter.api.Test
	public void tst() throws Exception {
		assertNotNull(itineraryRepository);
		assertNotNull(loginService);
		assertNotNull(cityRepository);
		when(itineraryRepository.getItinerariesFrom(any(Credentials.class),anyInt())).then(new Answer<ItineraryDTO[]>() {
			@Override
			public ItineraryDTO[] answer(InvocationOnMock invocation) throws Throwable {
				Integer city = invocation.getArgument(1);
				return getItinerariesFrom(city);
			}
		});
		when(loginService.getUserToken(anyString(), anyString())).thenReturn(new Credentials());
		when(loginService.getUserToken(isNull(), isNull())).thenReturn(new Credentials());
		when(cityRepository.cityExists(any(Credentials.class), anyInt())).thenReturn(true);
		when(cityRepository.cityExists(any(Credentials.class), isNull())).thenReturn(false);
		List<ItineraryDTO> dtos = itineraryService.getShorterRoute(1,8, CalculateMode.CONNECTIONS);
		assertNotNull(dtos);
		assertTrue(dtos.size()==2);
		assertTrue(dtos.get(0).getOriginCity().getId() == 1);
		assertTrue(dtos.get(1).getOriginCity().getId() == 7);
		assertTrue(dtos.get(1).getDestinyCity().getId() == 8);
	}
	
	private ItineraryDTO[] getItinerariesFrom(Integer from) {
		ItineraryDTO[] dtos;
		switch(from.intValue()) {
		case 1:
			dtos = new ItineraryDTO[4];
			dtos[0] = getDTO(1,2);
			dtos[1] = getDTO(1,4);
			dtos[2] = getDTO(1,9);
			dtos[3] = getDTO(1,7);
			return dtos;
		case 2:
			dtos = new ItineraryDTO[2];
			dtos[0] = getDTO(2,1);
			dtos[1] = getDTO(2,3);
			return dtos;
		case 3:
			dtos = new ItineraryDTO[1];
			dtos[0] = getDTO(3,6);
			return dtos;
		case 4:
			dtos = new ItineraryDTO[1];
			dtos[0] = getDTO(4,5);
			return dtos;
		case 5:
			dtos = new ItineraryDTO[2];
			dtos[0] = getDTO(5,12);
			dtos[1] = getDTO(5,8);
			return dtos;
		case 6:
			dtos = new ItineraryDTO[1];
			dtos[0] = getDTO(6,8);
			return dtos;
		case 7:
			dtos = new ItineraryDTO[1];
			dtos[0] = getDTO(7,8);
			return dtos;
		case 9:
			dtos = new ItineraryDTO[1];
			dtos[0] = getDTO(9,10);
			return dtos;
		case 10:
			dtos = new ItineraryDTO[1];
			dtos[0] = getDTO(10,11);
			return dtos;
		case 11:
			dtos = new ItineraryDTO[1];
			dtos[0] = getDTO(11,8);
			return dtos;
		case 12:
			dtos = new ItineraryDTO[1];
			dtos[0] = getDTO(12,8);
			return dtos;
		default:
			return new ItineraryDTO[0];
		}
	}
	
	private ItineraryDTO getDTO(Integer from,Integer to) {
		ItineraryDTO dto = new ItineraryDTO();
		CityDTO origin = new CityDTO();
		origin.setId(from);
		origin.setName("Name of "+from);
		dto.setOriginCity(origin);
		CityDTO destiny = new CityDTO();
		destiny.setId(to);
		destiny.setName("Name of "+to);
		dto.setDestinyCity(destiny);
		dto.setDepartureTime("10:00:00");
		dto.setArrivalTime("12:00:00");
		return dto;
	}

}
