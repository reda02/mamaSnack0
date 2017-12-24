package com.mamasnack.dao;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mamasnack.entities.Commande;

	public interface CommandeRepository extends JpaRepository<Commande, Long>{

      
		
		@Query("select o from Commande o JOIN o.items c JOIN c.produit r "
				+ "where c.produit.idProduit = r.idProduit"
				+ " and r.idProduit like :x")
		//@Query("SELECT o FROM Commande o WHERE  o.idCommande like :x")
		public List<Commande> findCommandesParProduit(@Param("x") Long c);
		
		@Query("SELECT o FROM Commande o INNER JOIN  o.user c WHERE c.idUser like :x")
		public List<Commande> findCommandeByUser(@Param("x") Long idUser);
		
		@Query("SELECT o FROM Commande o WHERE  o.idCommande like :x")
		public Commande findOne(@Param("x") Long idCmd); 
		
	}