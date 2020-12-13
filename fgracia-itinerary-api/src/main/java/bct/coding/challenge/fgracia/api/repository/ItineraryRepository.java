package bct.coding.challenge.fgracia.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import bct.coding.challenge.fgracia.api.entity.Itinerary;

public interface ItineraryRepository extends CrudRepository<Itinerary, Integer>{

	public List<Itinerary> findAllByOriginCityName(String city);
	
	public List<Itinerary> findAllByOriginCityId(Integer originCityId);
	
	public List<Itinerary> findAllByDestinyCityId(Integer destinyCityId);
	
	public List<Itinerary> findAllByOriginCityIdAndDestinyCityId(Integer originCityId, Integer destinyCityId);
	
}
