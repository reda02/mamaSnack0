package com.mamasnack.metier;

import java.io.IOException;
import java.util.List;

import com.mamasnack.entities.Role;
import com.mamasnack.entities.User;

public interface UserMetier {
	
	public String addUser(User u);
	public String supprimerUser(Long idUser) ;
	public String modifierUser(User u);
	public String modifierphotoPhUser(User u);
	public User getUser(Long idUser); 
	
	public List<User> listUsers();
	public List<User> findUserbyName(String nameUser);
	public List<User> findUsersbyCity(String ville);
	
	//   Gestion Role
	public String attribueRole(Long iduser, Long idrole);
	public Role getRole(Long idRole); 
	public String addRole(Role e);
	public List<User> findUsersbyRole(Long idRole);
	public List<Role> findRolebyUser(Long iduser);
	public List<Role> findRolebyUserEmail(String email);
	public String retirerRole(Long idUser, Long idRole);
	public String checkLogin(User u);
	public User findUsesbyEmail(String email);
	public List<Role> getRoles();
	public String supprimerRole(Long idRole);
	public String InitPwdVerifierEmail(String email) throws IOException;
	public String updatePassword(User u);
	
	
	
	

}
