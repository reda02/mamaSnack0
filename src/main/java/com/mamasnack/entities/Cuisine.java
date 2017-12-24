package com.mamasnack.entities;

import java.io.Serializable;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
@JsonSerialize
@JsonInclude
@Entity
public class Cuisine  implements Serializable{

	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6079773289698270780L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idCuisine ;
	private String nameCuisine ;
	private String langue ;
	
	//@JsonManagedReference("cuisine")
	@OneToMany(mappedBy="cuisine", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<Produit>produit ;
	
	public Long getIdCuisine() {
		return idCuisine;
	}
	public void setIdCuisine(Long idCuisine) {
		this.idCuisine = idCuisine;
	}
	public String getNameCuisine() {
		return nameCuisine;
	}
	public void setNameCuisine(String nameCuisine) {
		this.nameCuisine = nameCuisine;
	}
	public String getLangue() {
		return langue;
	}
	public void setLangue(String langue) {
		this.langue = langue;
	}
    @JsonIgnore
	public Collection<Produit> getProduit() {
		return produit;
	}
	@JsonSetter
	public void setProduit(Collection<Produit> produit) {
		this.produit = produit;
	}
	public Cuisine() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Cuisine(String nameCuisine, String langue) {
		super();
		this.nameCuisine = nameCuisine;
		this.langue = langue;
	}
	

}
