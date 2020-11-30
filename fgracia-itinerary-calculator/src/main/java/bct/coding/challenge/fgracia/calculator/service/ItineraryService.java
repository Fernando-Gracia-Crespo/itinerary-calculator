package bct.coding.challenge.fgracia.calculator.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bct.coding.challenge.fgracia.calculator.dto.ItineraryDTO;
import bct.coding.challenge.fgracia.calculator.exceptions.APIAccessException;
import bct.coding.challenge.fgracia.calculator.exceptions.NoValidCityException;
import bct.coding.challenge.fgracia.calculator.exceptions.NoValidTimeException;
import bct.coding.challenge.fgracia.calculator.repository.ItineraryRepository;

@Service
public class ItineraryService {
	
	public enum CalculateMode { CONNECTIONS, TIME };
	
	@Autowired
	private ItineraryRepository itineraryRepository;
	
	public List<ItineraryDTO> getShorterRoute(String from, String to, CalculateMode mode) throws APIAccessException, NoValidCityException, NoValidTimeException{
		// We store the shortest found. If we detect the path we are going is already bigger (in terms of connections or time) we stop looking in this path. Initial value is MAX_VALUE to represent we did not found one yet
		int shortest = Integer.MAX_VALUE;
		// We store the cities already visited in the current path so we avoid loops. the cities are stored in the following pattern ##city1##city2##city3##
		String visited = "##" + from + "##";
		// Initial size is 0 as we have not yet process any itinerary
		Path shortestPath = getParcialShorterRoute(from, to, visited, 0, shortest, mode);
		if(shortestPath==null || shortestPath.dtos == null || shortestPath.dtos.isEmpty()) {
			throw new NoValidCityException("City error. Either one city is non existant or the origin and destination cities are not conected");
		}
		return shortestPath.dtos;
	}
	
	private Path getParcialShorterRoute(String from, String to, String visited, int currentSize, int shortestFound, CalculateMode mode) throws APIAccessException, NoValidCityException, NoValidTimeException{
		ItineraryDTO[] itineraries = itineraryRepository.getItinerariesFrom(from);
		Path shortestFromHere = null;
		int shortestFoundIncludingThisBranch = shortestFound;
		for(ItineraryDTO itinerary:itineraries) {
			// We get the size of this particular itinerary
			int destSize = getSize(itinerary, mode);
			// We check if we have arrived to the destination
			if(itinerary.getDestinyCity().equalsIgnoreCase(to)) {
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
			} else if(!visited.contains("##"+itinerary.getDestinyCity()+"##")) {
				// we have not yet visited this city on this path, no loop has been made, we get the shortest path from here
				String newVisited = visited + itinerary.getDestinyCity() + "##";
				int newCurrentSize = currentSize + destSize;
				// We check if we are still under the size of the shortest path found to this point  
				if(newCurrentSize<shortestFound) {
					Path shortestFromDestination = getParcialShorterRoute(itinerary.getDestinyCity(), to, newVisited, newCurrentSize, shortestFoundIncludingThisBranch, mode);
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
		}catch(Exception e){
			throw new NoValidTimeException("There is an invalid time value",e);
		}
	}
	
	private class Path {
		List<ItineraryDTO> dtos = new ArrayList<ItineraryDTO>();
		int weight = Integer.MAX_VALUE;
	}
	
	
}
