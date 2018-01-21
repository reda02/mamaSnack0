package com.mamasnack.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mamasnack.entities.LigneCommande;

 public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long>{

	 @Query("SELECT o FROM LigneCommande o INNER JOIN  o.commande c WHERE c.idCommande = :x")
	public List<LigneCommande> findAllLigneDeCommande(@Param("x") long commandeId);

	 @Query("SELECT p FROM LigneCommande p INNER JOIN p.produit c WHERE  c.idProduit like :x")
	public List<LigneCommande> getAllLignebyIdProd(@Param("x")long prodId); 
	
	
}