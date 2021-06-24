package bct.coding.challenge.fgracia.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="ITINERARY")
public class Itinerary implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "origin_city")
	private City originCity;
	
	@ManyToOne
	@JoinColumn(name = "destiny_city")
	private City destinyCity;
	
	/*@Column(name="origin_city")
	private String originCity;
	
	@Column(name="destiny_city")
	private String destinyCity;*/
	
	@Column(name="departure_time")
	private String departureTime;
	
	@Column(name="arrival_time")
	private String arrivalTime;

	
}
