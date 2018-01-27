package com.mamasnack.metier;

import java.util.List;

import com.mamasnack.entities.Commande;
import com.mamasnack.entities.LigneCommande;
import com.mamasnack.entities.Panier;
import com.mamasnack.entities.User;


public interface CommandeMetier {
	
	   public Commande enrigistrerCommande(Panier p , User u);
	
	    // Recherche Multicritère 
		public List<Commande> listCommandes();
		public Commande getCommandeById(Long idCommande);
		public List<Commande> listCommandesParUser(Long idUser) ;
		public List<Commande> listCommandesParProduit(Long idProduit) ;
		public String addCommande(Commande commande);
		// Gerer Lignes de Commandes
		
		public List<LigneCommande> getAllLigneDeCommande(long commandeId);
	    public LigneCommande getLigneDeCommandeById(long commandeId, long ligneDeCommandeId);
//	    /public String addLigneDeCommande(long commandeId, LigneCommande ligneDeCommande);
	    public String modifyLigneDeCommande(LigneCommande ligneDeCommandeToSet);
	    public LigneCommande removeLigneDeCommande(long commandeId, long ligneDeCommandeId);

	    public String addLigneDeCommande(LigneCommande ligneDeCommande);
	    public List<LigneCommande> getAllLignebyIdProd(long prodId);
}
