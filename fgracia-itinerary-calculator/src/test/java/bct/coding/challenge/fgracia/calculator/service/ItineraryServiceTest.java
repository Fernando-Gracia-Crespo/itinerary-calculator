package bct.coding.challenge.fgracia.calculator.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import bct.coding.challenge.fgracia.calculator.dto.ItineraryDTO;
import bct.coding.challenge.fgracia.calculator.repository.ItineraryRepository;
import bct.coding.challenge.fgracia.calculator.service.ItineraryService.CalculateMode;

@SpringBootTest
public class ItineraryServiceTest {

	@Mock
	private ItineraryRepository itineraryRepository;
	
	@InjectMocks
	private ItineraryService itineraryService;
	
	@org.junit.jupiter.api.Test
	public void tst() throws Exception {
		assertNotNull(itineraryRepository);
		when(itineraryRepository.getItinerariesFrom(anyString())).then(new Answer<ItineraryDTO[]>() {
			@Override
			public ItineraryDTO[] answer(InvocationOnMock invocation) throws Throwable {
				String city = invocation.getArgument(0);
				return getItinerariesFrom(city);
			}
		});
		List<ItineraryDTO> dtos = itineraryService.getShorterRoute("a","h", CalculateMode.CONNECTIONS);
		assertNotNull(dtos);
		assertTrue(dtos.size()==2);
		assertTrue(dtos.get(0).getOriginCity().equals("a"));
		assertTrue(dtos.get(1).getOriginCity().equals("g"));
		assertTrue(dtos.get(1).getDestinyCity().equals("h"));
	}
	
	private ItineraryDTO[] getItinerariesFrom(String from) {
		if("a".equals(from)) {
			ItineraryDTO[] dtos = new ItineraryDTO[4];
			dtos[0] = getDTO("a","b");
			dtos[1] = getDTO("a","d");
			dtos[2] = getDTO("a","i");
			dtos[3] = getDTO("a","g");
			return dtos;
		} else if("b".equals(from)) {
			ItineraryDTO[] dtos = new ItineraryDTO[1];
			dtos[0] = getDTO("b","c");
			return dtos;
		} else if("c".equals(from)) {
			ItineraryDTO[] dtos = new ItineraryDTO[1];
			dtos[0] = getDTO("c","f");
			return dtos;
		} else if("f".equals(from)) {
			ItineraryDTO[] dtos = new ItineraryDTO[1];
			dtos[0] = getDTO("f","h");
			return dtos;
		} else if("d".equals(from)) {
			ItineraryDTO[] dtos = new ItineraryDTO[1];
			dtos[0] = getDTO("d","e");
			return dtos;
		} else if("e".equals(from)) {
			ItineraryDTO[] dtos = new ItineraryDTO[2];
			dtos[0] = getDTO("e","l");
			dtos[1] = getDTO("e","h");
			return dtos;
		} else if("l".equals(from)) {
			ItineraryDTO[] dtos = new ItineraryDTO[1];
			dtos[0] = getDTO("l","h");
			return dtos;
		} else if("i".equals(from)) {
			ItineraryDTO[] dtos = new ItineraryDTO[1];
			dtos[0] = getDTO("i","j");
			return dtos;
		} else if("j".equals(from)) {
			ItineraryDTO[] dtos = new ItineraryDTO[1];
			dtos[0] = getDTO("j","k");
			return dtos;
		} else if("k".equals(from)) {
			ItineraryDTO[] dtos = new ItineraryDTO[1];
			dtos[0] = getDTO("k","h");
			return dtos;
		} else if("g".equals(from)) {
			ItineraryDTO[] dtos = new ItineraryDTO[1];
			dtos[0] = getDTO("g","h");
			return dtos;
		}
		return new ItineraryDTO[0];
	}
	
	private ItineraryDTO getDTO(String from,String to) {
		ItineraryDTO dto = new ItineraryDTO();
		dto.setOriginCity(from);
		dto.setDestinyCity(to);
		dto.setDepartureTime("10:00:00");
		dto.setArrivalTime("12:00:00");
		return dto;
	}

}
