package com.mamasnack.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Reglement implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3322881123352633597L;
	@Id
	private int numCarte;
	private Double montant;
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date dateRegelement;
	private int codeCarte;
	private String typeCarte;
	
	@OneToMany(mappedBy="reglement")
	private Collection<Commande>commande;

	public int getNumCarte() {
		return numCarte;
	}

	public void setNumCarte(int numCarte) {
		this.numCarte = numCarte;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public Date getDateRegelement() {
		return dateRegelement;
	}

	public void setDateRegelement(Date dateRegelement) {
		this.dateRegelement = dateRegelement;
	}

	public int getCodeCarte() {
		return codeCarte;
	}

	public void setCodeCarte(int codeCarte) {
		this.codeCarte = codeCarte;
	}

	public String getTypeCarte() {
		return typeCarte;
	}

	public void setTypeCarte(String typeCarte) {
		this.typeCarte = typeCarte;
	}

	public Collection<Commande> getCommande() {
		return commande;
	}

	public void setCommande(Collection<Commande> commande) {
		this.commande = commande;
	}
	

}
