package bct.coding.challenge.fgracia.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="CITY")
public class City implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(name="name")
	private String name;

}
