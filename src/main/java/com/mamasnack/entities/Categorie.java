package com.mamasnack.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
@JsonSerialize
@JsonInclude
@Entity
public class Categorie implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2817979565672522423L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idCategorie;
	private String nomCategorie;
	@Size(min=4)
	private String description;
	@Lob
	private byte[] photo;
	private String nomPhoto;
	//@JsonManagedReference("cat")@OneToMany(mappedBy="user")
	@OneToMany(mappedBy="categorie", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Produit>produit ;
	
	
	
	public Long getIdCategorie() {
		return idCategorie;
	}
	public void setIdCategorie(Long idCategorie) {
		this.idCategorie = idCategorie;
	}
	public String getNomCategorie() {
		return nomCategorie;
	}
	public void setNomCategorie(String nomCategorie) {
		this.nomCategorie = nomCategorie;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public String getNomPhoto() {
		return nomPhoto;
	}
	public void setNomPhoto(String nomPhoto) {
		this.nomPhoto = nomPhoto;
	}
	
	@JsonIgnore
	public List<Produit> getProduit() {
		return produit;
	}
	@JsonSetter
	public void setProduit(List<Produit> produit) {
		this.produit = produit;
	}
	public Categorie() {
		super();
		
	}
	public Categorie(String nomCategorie, String description, byte[] photo, String nomPhoto) {
		super();
		this.nomCategorie = nomCategorie;
		this.description = description;
		this.photo = photo;
		this.nomPhoto = nomPhoto;
	}
	
	
}
