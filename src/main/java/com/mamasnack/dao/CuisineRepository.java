package com.mamasnack.dao;

 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mamasnack.entities.Cuisine;

public interface CuisineRepository extends JpaRepository<Cuisine, Long>{
	@Query("SELECT o FROM Cuisine o WHERE  o.idCuisine like :x")
	public Cuisine getfinOne(@Param("x") Long idC); 
	
	
} 