package com.mamasnack.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.mamasnack.entities.Produit;




public interface ProduitRepository extends JpaRepository<Produit, Long>{

	@Query("SELECT o FROM Produit o INNER JOIN  o.categorie c WHERE c.idCategorie like :x")
	public List<Produit> findProduitsParCategorie(@Param("x") Long idCat);
	
	@Query("SELECT o FROM Produit o INNER JOIN  o.cuisine c WHERE c.idCuisine like :x")
	public List<Produit> findProduitsParCuisine(@Param("x") Long idCuisine);

	@Query("SELECT p FROM Produit p WHERE p.steleted = true")
	public List<Produit> findProduitsSelectionne();
	
	@Query("SELECT o FROM Produit o INNER JOIN o.user u WHERE u.idUser like :x")
	public List<Produit> finProduitbyUser(@Param("x") Long iduser);

	@Query("SELECT p FROM Produit p WHERE  p.idProduit like :x")
	public Produit findOne(@Param("x") Long idPro);
	
	@Query("SELECT p FROM Produit p WHERE  p.idProduit like :x")
	public boolean existsProduit(@Param("x") Long idProduit);
	 
	@Query("SELECT p FROM Produit p WHERE  p.description like %:mc%")
	 public List<Produit> findproduitParMotCle(@Param("mc") String mc); 
	

	  @Transactional
	  @Modifying
	  @Query("delete from Produit c where c.idProduit = ?1")
	public void deleteByIdProd(@Param("mc") Long id);
}