package com.mamasnack.dao;
  


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.mamasnack.entities.User;




public interface UserRepository extends JpaRepository<User, Long>{ 
	  
	   @Query("SELECT o FROM User o  WHERE  o.nomUser like :x")
		public List<User>  findUserByName(@Param("x") String nom);
	 
	   @Query("SELECT o FROM User o INNER JOIN  o.role c  WHERE  c.idRole like :x")
		public List<User>  findUserbyRole(@Param("x") Long idRole);

	   @Query("SELECT o FROM User o WHERE  o.idUser like :x")
     	public User findOne(@Param("x") Long idUser);

	   @Query("SELECT o FROM User o WHERE  o.idUser like :x")
	   public boolean existsparID(@Param("x")  Long idUser);

	   @Query("SELECT o FROM User o WHERE  o.email like :x")
	   public User findUsesbyEmail(@Param("x")  String email);

	   @Query("SELECT o FROM User o INNER JOIN  o.role c  WHERE  c.roleName like 'mama'  AND  o.ville like :x")
		public List<User>  findUserByCity(@Param("x") String ville);

	    @Modifying
	    @Transactional
		@Query("update User u set u.nomUser = :xnom ,u.prenomUser = :xprenom"
				+ ",u.adresse = :xadress,u.codePostale = :xcodePostal"
				+ ",u.dateNaissonce = :xdatenaiss"
				+ ",u.email = :xemail,u.tel = :xtel"
				+ " where u.idUser like :xid")
	    public void update(@Param("xid")  Long xid ,@Param("xnom")  String nom, @Param("xprenom")  String prenom
	    		,@Param("xadress")  String nadress,@Param("xcodePostal")  int codePostal,@Param("xdatenaiss")  Date datenaiss
	    		,@Param("xemail")  String email,@Param("xtel")  int tel);
	    
	    @Query("SELECT o FROM User o WHERE  o.email like :x")
     	public User findOnebyemail(@Param("x") String email);
	    
	    @Modifying
	    @Transactional
		@Query("update User u set u.password = :xpwd"
				+ " where u.email like :xemail")
	    public void updatePwd(@Param("xemail")  String xemail ,@Param("xpwd")  String pwd);

	    
	    @Modifying
	    @Transactional
		@Query("update User u set u.nomUser = :xphoto "
				+ " where u.idUser like :xid")
	    public void updatePhoto(@Param("xid")  Long xid ,@Param("xphoto")  String photo);
	    
	    
	    
}