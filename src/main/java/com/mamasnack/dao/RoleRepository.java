 package com.mamasnack.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mamasnack.entities.Role;




public interface RoleRepository extends JpaRepository<Role, Long>{

	   @Query("SELECT o FROM Role o WHERE  o.idRole like :x")
	   public Role findOne(@Param("x") Long idRole);
	  
	   @Query("SELECT o FROM Role o INNER JOIN o.User u WHERE u.idUser like :x")
	   public List<Role> findRolebyUser(@Param("x") Long iduser); 


}