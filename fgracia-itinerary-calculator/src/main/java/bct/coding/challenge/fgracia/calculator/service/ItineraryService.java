package bct.coding.challenge.fgracia.calculator.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	
	public List<ItineraryDTO> getShorterRoute(Integer from, Integer to, CalculateMode mode) throws APIAccessException, NoValidCityException, NoValidTimeException, NoReachableCityException{
		Credentials credentials = loginService.getUserToken(apiUser, apiPass);
		if(!cityRepository.cityExists(credentials, from)) {
			throw new NoValidCityException("City Id not valid: "+from);
		}
		if(!cityRepository.cityExists(credentials, to)) {
			throw new NoValidCityException("City Id not valid: "+to);
		}
		// We store the shortest found. If we detect the path we are going is already bigger (in terms of connections or time) we stop looking in this path. Initial value is MAX_VALUE to represent we did not found one yet
		int shortest = Integer.MAX_VALUE;
		// We store the cities already visited in the current path so we avoid loops. the cities are stored in the following pattern ##city1##city2##city3##
		String visited = "##" + from + "##";
		// Initial size is 0 as we have not yet process any itinerary
		Path shortestPath = getParcialShorterRoute(credentials, from, to, visited, 0, shortest, mode);
		if(shortestPath==null || shortestPath.dtos == null || shortestPath.dtos.isEmpty()) {
			throw new NoReachableCityException("The origin and destination cities are not conected");
		}
		return shortestPath.dtos;
	}
	
	private Path getParcialShorterRoute(Credentials credentials, Integer from, Integer to, String visited, int currentSize, int shortestFound, CalculateMode mode) throws APIAccessException, NoValidCityException, NoValidTimeException{
		ItineraryDTO[] itineraries = itineraryRepository.getItinerariesFrom(credentials, from);
		Path shortestFromHere = null;
		int shortestFoundIncludingThisBranch = shortestFound;
		for(ItineraryDTO itinerary:itineraries) {
			// We get the size of this particular itinerary
			int destSize = getSize(itinerary, mode);
			// We check if we have arrived to the destination
			if(itinerary.getDestinyCity().getId() == to) {
				// we found the destination, we check if this path is the shortest found yet
				int size = currentSize + destSize;
				// We only consider this path if it is not bigger than the shortest found yet
				if(size < shortestFound) {
					// We have not yet discover any shorter path.
					// we check if we have found another path from here (it can happen if the case AB + BC < AC is present)
					if(shortestFromHere==null || shortestFromHere.weight > size) {
						// The one we found is shorter than anyone found yet from this location
						shortestFromHere = new Path();
						shortestFromHere.dtos = new ArrayList<ItineraryDTO>();
						shortestFromHere.dtos.add(itinerary);
						shortestFromHere.weight = size;
						// We set the new shortest found
						shortestFoundIncludingThisBranch = size;
					}
				}
			} else if(!visited.contains("##"+itinerary.getDestinyCity().getId()+"##")) {
				// we have not yet visited this city on this path, no loop has been made, we get the shortest path from here
				String newVisited = visited + itinerary.getDestinyCity().getId() + "##";
				int newCurrentSize = currentSize + destSize;
				// We check if we are still under the size of the shortest path found to this point  
				if(newCurrentSize<shortestFound) {
					Path shortestFromDestination = getParcialShorterRoute(credentials, itinerary.getDestinyCity().getId(), to, newVisited, newCurrentSize, shortestFoundIncludingThisBranch, mode);
					// If shortestFromDestination == null it means the paths from here does not include the shortest found yet or the destination city is unreachable
					if(shortestFromDestination!=null) {
						// We have found the shortest found yet, we add this destination at the start of the list
						if(shortestFromHere==null || shortestFromHere.weight > shortestFromDestination.weight) {
							shortestFromDestination.dtos.add(0, itinerary);
							shortestFromHere = shortestFromDestination;
							shortestFoundIncludingThisBranch = shortestFromDestination.weight;
						}
					}
				}
			}
		}
		return shortestFromHere;
	}
	
	private int getSize(ItineraryDTO itinerary, CalculateMode mode) throws NoValidTimeException{
		// If there is any problem, we return MAX_VALUE so this itinerary will be discarded
		if(itinerary == null) return Integer.MAX_VALUE;
		switch(mode) {
		case CONNECTIONS:
			// In terms of number of connections, all itineraries are direct connections, so its size is 1
			return 1;
		case TIME:
			// We calculate the temporal size in number of seconds
			return durationInSeconds(itinerary);
		default:
			return Integer.MAX_VALUE;
		}
	}
	
	
	
	private int durationInSeconds(ItineraryDTO itinerary) throws NoValidTimeException{
		// We parse the times with the format HH:mm:ss and we return its length in seconds 
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		try {
			Date departure = sdf.parse(itinerary.getDepartureTime());
			Date arrival = sdf.parse(itinerary.getArrivalTime());
			long lengthInMillis = arrival.getTime() - departure.getTime();
			return (int) (lengthInMillis/1000l);
		}catch(ParseException e){
			throw new NoValidTimeException(itinerary);
		}
	}
	
	private class Path {
		List<ItineraryDTO> dtos = new ArrayList<ItineraryDTO>();
		int weight = Integer.MAX_VALUE;
	}
	
	
}
