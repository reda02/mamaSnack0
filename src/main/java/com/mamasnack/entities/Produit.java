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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
	private String photo1;
	private String photo2;
	private String photo3;
	private String photo4;
	private String photo5;
	private int quantite;
	
    @JsonFormat(pattern="dd-MM-yyyy")
	private Date dateAjout;
    
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="idCategorie")
	private Categorie categorie ;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
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
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="idCuisine")
	private Cuisine cuisine ;

	//@JsonBackReference("produit")
	@OnDelete(action = OnDeleteAction.CASCADE)
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



	@JsonSetter
	public void setItems(Collection<LigneCommande> items) {
		this.items = items;
	}

	public String getPhoto1() {
		return photo1;
	}

	public void setPhoto1(String photo1) {
		this.photo1 = photo1;
	}

	public String getPhoto2() {
		return photo2;
	}

	public void setPhoto2(String photo2) {
		this.photo2 = photo2;
	}

	public String getPhoto3() {
		return photo3;
	}

	public void setPhoto3(String photo3) {
		this.photo3 = photo3;
	}

	public String getPhoto4() {
		return photo4;
	}

	public void setPhoto4(String photo4) {
		this.photo4 = photo4;
	}

	public String getPhoto5() {
		return photo5;
	}

	public void setPhoto5(String photo5) {
		this.photo5 = photo5;
	}

}
