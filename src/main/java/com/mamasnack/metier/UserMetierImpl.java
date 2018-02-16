package com.mamasnack.metier;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mamasnack.dao.RoleRepository;
import com.mamasnack.dao.UserRepository;
import com.mamasnack.entities.Produit;
import com.mamasnack.entities.Role;
import com.mamasnack.entities.SendEmail;
import com.mamasnack.entities.User;


@Service
public class UserMetierImpl implements UserMetier{

	@Autowired
	private UserRepository userRepository ;
	@Autowired
	private RoleRepository roleRepository ;
	
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public String addUser(User u) {
		
		User user =userRepository.findOnebyemail(u.getEmail()) ;
		if( user!= null){
			
			logger.error(getClass().getName()+
				    "email n'est pas unique lors de l'exécution du web service addUser : ");

			return "NOK:EmailNONUnique";
			
		} else if (u.getIdUser() != null && !userRepository.existsById(u.getIdUser())) {
				logger.error(getClass().getName()+
					    "idUser est null ou email n'est pas unique lors de l'exécution du web service addUser : ");

				return "NOK";
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if(u.getPassword()!=null){
		u.setPassword(passwordEncoder.encode(u.getPassword()));
		}
		userRepository.save(u);
		return "OK";
	}

	@Override
	public String supprimerUser(Long idUser) {
    
		User user= getUser(idUser) ;
		if (idUser == null ) {
			//	throw new EntityExistsException("There is already existing entity with such ID in the database.");
			
				logger.error(getClass().getName()+
					    "idUser est null de l'exécution du web service supprimerUser : ");

				return "NOK";
			}else if (user.getIdUser() ==null){
				
				logger.error(getClass().getName()+
					    "idUser non existe dans BD ;erreur est produite lors de l'exécution du web service supprimerUser : ");
				return "NOK";
			}
		
	
	 userRepository.deleteById(idUser);
	 return "OK";
	
	}

	@Override
	public String modifierUser(User u) {	
       User user =userRepository.findOnebyemail(u.getEmail()) ;
		
		if( user!= null){
			
			logger.error(getClass().getName()+
				    "email n'est pas unique lors de l'exécution du web service addUser : ");

			return "NOK:EmailNONUnique";
			
		}
		if (u.getIdUser() != null && !userRepository.existsById(u.getIdUser())) {
		//	throw new EntityExistsException("There is already existing entity with such ID in the database.");
			logger.error(getClass().getName()+
				    "une erreur est produite lors de l'exécution du web service updateuser : ");

			return "NOK";
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
		
		
		if(u.getPassword()!=null){
			u.setPassword(passwordEncoder.encode(u.getPassword()));
			}
		userRepository.update(u.getIdUser(),u.getNomUser(),u.getPrenomUser(),u.getAdresse()
				,u.getCodePostale(),u.getDateNaissonce(),u.getEmail(),u.getTel());
		return "OK";
	}



	@Override
	public User getUser(Long idUser) {
	
		User user= userRepository.findOne(idUser) ;
		if (user==null)throw new RuntimeException("User inexistant !");
		
		return user;
	}

	@Override
	public List<User> listUsers() {
		
		return userRepository.findAll();
	}

	@Override
	public List<User> findUserbyName(String nameUser) {
	
		return userRepository.findUserByName(nameUser);
	}
	
	@Override
	public List<User> findUsersbyCity(String ville) {
	
		return userRepository.findUserByCity(ville);
	}

	@Override
	public String attribueRole(Long idUser, Long idRole) {
	
		User u = getUser(idUser);
		Role r = getRole(idRole);
		List<Role> roles = findRolebyUser(idUser);
		//for(Role roul : roles){}
		
		   if(roles.contains(r)){
				logger.error(getClass().getName()+
					    "le role existe deja "+r.getIdRole());
				return "NOK";
			
		}
				roles.add(r);
				u.setRole(roles); 
				userRepository.save(u);
				return "OK";
		

	}

	@Override
	public String retirerRole(Long idUser, Long idRole) {
	
		User u = getUser(idUser);
		Role r = getRole(idRole);
		List<Role> roles = findRolebyUser(idUser);
		 //for(Role roul : roles){
		 //	System.out.println("le role existe dans BD "+roul.getIdRole());
	     //    }
		   if(roles.contains(r) ){
				roles.remove(r);
				u.setRole(roles); 
				userRepository.save(u);
				return "OK";
			}else{
				//System.out.println("le role not existe in BD ");
				logger.error(getClass().getName()+
					    "le role not existe in BD  !  : ");
				return "NOK";
			}
	   }
	
	@Override
	public String addRole(Role e) {
	
		if (e.getIdRole() != null && !userRepository.existsById(e.getIdRole())) {
			logger.error(getClass().getName()+
				    "IdRole est null de l'exécution du web service addRole : ");

			return "NOK";
	}
		roleRepository.save(e);
	    return "OK";
	}

	@Override
	public List<User> findUsersbyRole(Long idRole) {

		return userRepository.findUserbyRole(idRole);
	}

	@Override
	public List<Role> findRolebyUser(Long iduser) {

		return roleRepository.findRolebyUser(iduser);
	}
	@Override
	public Role getRole(Long idRole) {
		Role role= roleRepository.findOne(idRole) ;
		if (role==null)throw new RuntimeException("role inexistant !");
		return role;
	}

	@Override
	public User findUsesbyEmail(String email) {

		return userRepository.findUsesbyEmail(email);
	}
	@Override
	public String checkLogin(User u) {
		
		
		User user = findUsesbyEmail(u.getEmail());
        if(user!=null){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		if (passwordEncoder.matches(u.getPassword(), user.getPassword())) {
		    // Encode new password and store it
			return "OK";
		} else {
		    // Report error 
			return "NOK";
		}}else{
		return "NOK";
		}		
	}


		@Override
		public List<Role> getRoles() {

			return roleRepository.findAll();
		}

		@Override
		public String supprimerRole(Long idRole) {
			Role role= getRole(idRole);
			if (idRole == null ) {
				//	throw new EntityExistsException("There is already existing entity with such ID in the database.");
				
					logger.error(getClass().getName()+
						    "idRole est null de l'exécution du web service supprimerUser : ");

					return "NOK";
				}else if (role.getIdRole() ==null){
					
					logger.error(getClass().getName()+
						    "idRole non existe dans BD ;erreur est produite lors de l'exécution du web service supprimerUser : ");
					return "NOK";
				}
			
		
		 roleRepository.deleteById(idRole);
		 return "OK";
			// TODO Auto-generated method stub
		}

		@Override
		public List<Role> findRolebyUserEmail(String email) {
			// TODO Auto-generated method stub
			return roleRepository.findRolebyUser(email);
		}

		@Override
		public String InitPwdVerifierEmail(String email) throws IOException {
			// TODO Auto-generated method stub
	    String  SendEmail;
		User user = userRepository.findUsesbyEmail(email);
		
		 if(Objects.nonNull(user)) {
			 SendEmail sendEmail = new SendEmail();
			  SendEmail= sendEmail.sendmail(user.getEmail());
			}else{
				logger.error(getClass().getName()+
					    "une erreur est produite lors de l'exécution du web service modifyLigneDeCommande : ");
				return "NOK: user NON trouvable ";
			}
		return SendEmail;
		}

		@Override
		public String updatePassword(User u) {
			// TODO Auto-generated method stub
			  User user =userRepository.findUsesbyEmail(u.getEmail()) ;
				
			  if(!Objects.isNull(user)) {
					
					logger.error(getClass().getName()+
						    "email n'est pas unique lors de l'exécution du web service updatePassword : ");

					return "NOK:user non trouvable !";
					
				}
				if (u.getIdUser() != null && !userRepository.existsById(u.getIdUser())) {
				//	throw new EntityExistsException("There is already existing entity with such ID in the database.");
					logger.error(getClass().getName()+
						    "une erreur est produite lors de l'exécution du web service updatePassword : ");

					return "NOK";
				}
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			
				
				
				if(u.getPassword()!=null){
					u.setPassword(passwordEncoder.encode(u.getPassword()));
					}
				userRepository.updatePwd(u.getEmail(),u.getPassword());
				return "OK";
		}
		
		
		@Override
		public String modifierphotoPhUser(User u) {
			
			 
				if (u.getIdUser() != null && !userRepository.existsById(u.getIdUser())) {
				//	throw new EntityExistsException("There is already existing entity with such ID in the database.");
					logger.error(getClass().getName()+
						    "une erreur est produite lors de l'exécution du web service modifierphotoPhUser : ");

					return "NOK";
				}
				
				userRepository.updatePhoto(u.getIdUser(),u.getPhoto());
				return "OK";
			}
		

}
