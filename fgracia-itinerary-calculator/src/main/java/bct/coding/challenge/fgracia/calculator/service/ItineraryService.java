package bct.coding.challenge.fgracia.calculator.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import bct.coding.challenge.fgracia.calculator.auth.Credentials;
import bct.coding.challenge.fgracia.calculator.dto.ItineraryDTO;
import bct.coding.challenge.fgracia.calculator.exception.APIAccessException;
import bct.coding.challenge.fgracia.calculator.exception.NoReachableCityException;
import bct.coding.challenge.fgracia.calculator.exception.NoValidCityException;
import bct.coding.challenge.fgracia.calculator.exception.NoValidTimeException;
import bct.coding.challenge.fgracia.calculator.repository.CityRepository;
import bct.coding.challenge.fgracia.calculator.repository.ItineraryRepository;

@Service
public class ItineraryService {

	public CityRepository getCityRepository() {
		return cityRepository;
	}

	public enum CalculateMode { CONNECTIONS, TIME };
	
	@Autowired
	private ItineraryRepository itineraryRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	public LoginService loginService;
	
	@Value("${itinerary.api.user}")
	private String apiUser;

	@Value("${itinerary.api.pass}")
	private String apiPass;

	private Supplier<Credentials> credentialsSupplier = () -> loginService.getUserToken(apiUser,apiPass);

	public List<ItineraryDTO> getShorterRoute(Integer from, Integer to, CalculateMode mode){
		Credentials credentials = credentialsSupplier.get();
		parameterCheck(credentials,from,to);
		Optional<Path> path = recursiveSearch(credentials, from, to, new ArrayList<Integer>(), mode);
		return path.orElseThrow(
				() -> new NoReachableCityException("The origin and destination cities are not connected")
				).dtos;
	}

	private void parameterCheck(Credentials credentials, Integer from, Integer to){
		IntConsumer c = i -> {
			if (!cityRepository.cityExists(credentials, i)) {
				throw new NoValidCityException("City Id not valid " + i);
			}
		};
		c.accept(from);
		c.accept(to);
	}

	private Optional<Path> recursiveSearch(Credentials credentials, Integer from, Integer to, List<Integer> visited, CalculateMode mode){
		List<ItineraryDTO> itineraries = Arrays.asList(itineraryRepository.getItinerariesFrom(credentials, from));
		return itineraries.stream()
				.filter(i -> !visited.contains(i.getDestinyCity().getId()))
				.map(i -> calculatePath(credentials,i,visited,from,to,mode))
				.filter(p->p.weight!=Integer.MAX_VALUE)
				.sorted()
				.findFirst();
	}

	private Path calculatePath(Credentials credentials, ItineraryDTO itinerary, List<Integer> visited, Integer from, Integer to, CalculateMode mode){
		if(itinerary.getDestinyCity().getId()==to){
			return new Path(mode,itinerary);
		} else {
			List<Integer> newVisited = visited.stream().collect(Collectors.toList());
			newVisited.add(from);
			Optional<Path> path = recursiveSearch(credentials, itinerary.getDestinyCity().getId(), to, newVisited, mode);
			if(path.isPresent() && path.get().weight!=Integer.MAX_VALUE){
				path.get().addItineraryAtStart(itinerary);
			}
			// The constructor with no arguments give us a Path with weight=Integer.MAX_VALUE that will be discarded
			return path.orElseGet(Path::new);
		}
	}
	
	private class Path implements Comparable<Path>{
		List<ItineraryDTO> dtos;
		int weight;
		CalculateMode mode;

		ToIntFunction<ItineraryDTO> getSiZeFn =
				i -> (i==null)?Integer.MAX_VALUE:(mode==CalculateMode.CONNECTIONS)?1:duration(i);

		Path(CalculateMode mode, ItineraryDTO dto){
			this.mode = mode;
			dtos = new ArrayList<>();
			dtos.add(dto);
			weight = getSiZeFn.applyAsInt(dto);
		}

		public Path(){
			dtos = new ArrayList<>();
			weight = Integer.MAX_VALUE;
		};

		public void addItineraryAtStart(ItineraryDTO dto){
			dtos.add(0,dto);
			weight += getSiZeFn.applyAsInt(dto);
		}

		public int compareTo(Path other){
			return this.weight - other.weight;
		};

		private int duration(ItineraryDTO dto){
			try{
				int arrival = LocalTime.parse(dto.getArrivalTime(),DateTimeFormatter.ISO_LOCAL_TIME).toSecondOfDay();
				int departure = LocalTime.parse(dto.getDepartureTime(),DateTimeFormatter.ISO_LOCAL_TIME).toSecondOfDay();
				return  arrival - departure;
			} catch (DateTimeParseException e){
				throw new NoValidTimeException(dto);
			}
		}

	}
	
	
}
