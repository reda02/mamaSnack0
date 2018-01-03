package com.mamasnack.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonInclude
@Entity
public class Produit implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7247905930264412099L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idProduit;
	//@Size(min=1,max=4)
	private String designation;
	private String description;
	private Double prix;
	private boolean steleted;
	private String photoName;
	private byte[] file;
	private int quantite;
	
    @JsonFormat(pattern="dd-MM-yyyy")
	private Date dateAjout;
    
	@ManyToOne
	@JoinColumn(name="idCategorie")
	private Categorie categorie ;
	
	@ManyToOne
	@JoinColumn(name="idUser")
	private User user ;

	@JsonIgnore
	public User getUser() {
		return user;
	}
	
	@JsonSetter
	public void setUser(User user) {
		this.user = user;
	}
	@ManyToOne
	@JoinColumn(name="idCuisine")
	private Cuisine cuisine ;

	@JsonBackReference("produit")
	@OneToMany(mappedBy="produit" , fetch = FetchType.LAZY)
	private Collection<LigneCommande> items ;
	
	
	//@JsonBackReference("cuisine")
	@JsonIgnore
	public Cuisine getCuisine() {
		return cuisine;
	}
	@JsonSetter
	public void setCuisine(Cuisine cuisine) {
		this.cuisine = cuisine;
	}
	//@JsonBackReference("cat")
	@JsonIgnore
	public Categorie getCategorie() {
		return categorie;
	}
	@JsonSetter
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	public Long getIdProduit() {
		return idProduit;
	}
	public void setIdProduit(Long idProduit) {
		this.idProduit = idProduit;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrix() {
		return prix;
	}
	public void setPrix(Double prix) {
		this.prix = prix;
	}
	public boolean isSteleted() {
		return steleted;
	}
	public void setSteleted(boolean steleted) {
		this.steleted = steleted;
	}

	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}


	public Produit() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Date getDateAjout() {
		return dateAjout;
	}
	public void setDateAjout(Date dateAjout) {
		this.dateAjout = dateAjout;
	}



	//@JsonIgnore
	public Collection<LigneCommande> getItems() {
		return items;
	}
	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	@JsonSetter
	public void setItems(Collection<LigneCommande> items) {
		this.items = items;
	}

}
