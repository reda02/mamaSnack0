package com.mamasnack.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape= JsonFormat.Shape.OBJECT)
public enum EtatCommande  implements Serializable{
	
	Initiale,
	Preparation_Encours,
	Paiement_Accepte,
	Paiement_EnAttente,
	Livre,
	NonLivre,
	Dispoible,
	Annule,
	Remborse;
	
	 public String getStatus() {
	        return this.name();
	    }
	
	

}
