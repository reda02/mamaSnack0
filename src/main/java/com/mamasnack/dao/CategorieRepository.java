package com.mamasnack.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mamasnack.entities.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long>{
	@Query("SELECT o FROM Categorie o WHERE  o.idCategorie like :x")
	Categorie findOne(@Param("x") Long idC);
	

	
	
	
}
