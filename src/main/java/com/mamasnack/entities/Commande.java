package com.mamasnack.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
@JsonSerialize
@JsonInclude
@Entity
public class Commande  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4351232433456277747L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idCommande;
	
	private double total;
	
	@Column(name="etatCommande", nullable = false, length = 8 )
    @Enumerated(value = EnumType.STRING)
	private EtatCommande etatCommande;
	
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date dateCommnade;
	
	//@JsonManagedReference("commande")
	@OneToMany(mappedBy="commande" , fetch = FetchType.LAZY)
	private Collection<LigneCommande> items ;
	
	
	@ManyToOne
	@JoinColumn(name = "iduser", referencedColumnName = "idUser", nullable = false)
	private User user ;
	
	@ManyToOne
	@JoinColumn(name = "idmama", referencedColumnName = "idUser", nullable = false)
	private User mama ;
	
	@ManyToOne
	@JoinColumn(name="numCarte")
	private Reglement reglement;
	
	public Long getIdCommande() {
		return idCommande;
	}
	public void setIdCommande(Long idCommande) {
		this.idCommande = idCommande;
	}

	public Date getDateCommnade() {
		return dateCommnade;
	}
	public void setDateCommnade(Date dateCommnade) {
		this.dateCommnade = dateCommnade;
	}
	@JsonIgnore
	public Collection<LigneCommande> getItems() {
		return items;
	}
	@JsonSetter
	public void setItems(Collection<LigneCommande> items) {
		this.items = items;
	}
	//@JsonIgnore
	public User getUser() {
		return user;
	}
	@JsonSetter
	public void setUser(User user) {
		this.user = user;
	}
	
	public Commande() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EtatCommande getEtatCommande() {
		return etatCommande;
	}
	public void setEtatCommande(EtatCommande etatCommande) {
		this.etatCommande = etatCommande;
	}
	@JsonIgnore
	public Reglement getReglement() {
		return reglement;
	}
	@JsonIgnore
	public void setReglement(Reglement reglement) {
		this.reglement = reglement;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double d) {
		this.total = d;
	}

	
	
}
