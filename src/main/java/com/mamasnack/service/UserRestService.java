package com.mamasnack.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mamasnack.entities.Role;
import com.mamasnack.entities.User;
import com.mamasnack.metier.UserMetier;

@RestController

public class UserRestService {

	@Autowired
	private UserMetier userMetier ;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());



	@RequestMapping(value="/checkLogin",method=RequestMethod.POST)
	public String checkLogin(@RequestBody User u) throws JSONException {
		String Add = null;
		User user =null;
		List<Role> roles = new ArrayList<>();
		JSONObject resultat = new JSONObject();
		JSONArray tab = new JSONArray();
		try {
			Add =  userMetier.checkLogin(u);
			roles= userMetier.findRolebyUserEmail(u.getEmail());
			user = userMetier.findUsesbyEmail(u.getEmail());
			ObjectMapper mapper = new ObjectMapper(); 
			if (roles != null && !roles.isEmpty() && Add =="OK" ) {
				tab = new JSONArray(mapper.writeValueAsString(roles).toString());
				resultat.put("id", user.getIdUser());
				resultat.put("role", tab);
			}else{
				resultat.put("role", "");
			}
			
			resultat.put("Msg", Add);
			
			
		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service deleteuser : " + e.getMessage());
		}
		return resultat.toString();
	}
	
	
	@RequestMapping(value="/adduser",method=RequestMethod.POST)
	public String addUser(@RequestBody User u) throws JSONException { //@RequestBody les donneés de la req  il va les recupere dans le corp de la requte et il sont supposé au format jeson 
		String Add = null;
		JSONObject resultat = new JSONObject();
		try {

			Add =  userMetier.addUser(u);
			resultat.put("errMess", Add);
		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service adduser : " + e.getMessage());
		}
		return resultat.toString();
	}



	@RequestMapping(value="/deleteuser/{idUser}",method=RequestMethod.GET)
	public String deleteUser(@PathVariable Long idUser) throws JSONException {


		String delete = null;
		JSONObject resultat = new JSONObject();
		try {

			delete =  userMetier.supprimerUser(idUser);
			resultat.put("errMess", delete);
		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service deleteuser : " + e.getMessage());
		}
		return resultat.toString();
	}

	@RequestMapping(value="/updateuser",method=RequestMethod.POST)
	public String updateUser(@RequestBody User u) throws JSONException { 
		String update = null;
		JSONObject resultat = new JSONObject();
		try {

			update =  userMetier.modifierUser(u);

			resultat.put("updateMess", update);
			resultat.put("errMess", "");
		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service updateuser : " + e.getMessage());
		}
		return resultat.toString();
	}




	@RequestMapping(value="/getuser/{idUser}",method=RequestMethod.GET)
	public String getUser(@PathVariable Long idUser) throws JSONException {


		User user = null;
		JSONObject resultat = new JSONObject();
		try {

			user = userMetier.getUser(idUser);
			ObjectMapper mapper = new ObjectMapper(); 

			resultat.put("user", new  JSONObject(mapper.writeValueAsString(user).toString()));
			resultat.put("errMess", "");
		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service getuser : " + e.getMessage());
		}

		return resultat.toString();
	}


	@RequestMapping(value="/users",method=RequestMethod.GET)
	public String  getUsers() throws JSONException {

		List<User> users = new ArrayList<>();
		JSONObject resultat = new JSONObject();
		JSONArray tab = new JSONArray();
		try { 
			users = userMetier.listUsers();
			ObjectMapper mapper = new ObjectMapper(); 

			if (users != null && !users.isEmpty()) {
				tab = new JSONArray(mapper.writeValueAsString(users).toString());
				resultat.put("users", tab);
			}else{
				resultat.put("users", "");
			}
			
			resultat.put("errMess", "");  

		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service getUsers() : " + e.getMessage());
		}
		return resultat.toString();

	}


	@RequestMapping(value="/userbyName/{nameUser}",method=RequestMethod.GET)
	public String findUserbyName(@PathVariable String nameUser) throws JSONException {
		List<User> users = new ArrayList<>();
		JSONObject resultat = new JSONObject();
		JSONArray tab = new JSONArray();
		try { 
			users = userMetier.findUserbyName(nameUser);
			ObjectMapper mapper = new ObjectMapper(); 

			if (users != null && !users.isEmpty()) {
				tab = new JSONArray(mapper.writeValueAsString(users).toString());
				resultat.put("users", tab);
			}else{
				resultat.put("users", "");
			}
			
			resultat.put("errMess", "");  

		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service findUserbyName() : " + e.getMessage());
		}
		return resultat.toString();

	}
	@RequestMapping(value="/userbyCity/{ville}",method=RequestMethod.GET)
	public String findUserbyCity(@PathVariable String ville) throws JSONException {
		List<User> users = new ArrayList<>();
		JSONObject resultat = new JSONObject();
		JSONArray tab = new JSONArray();
		try { 
			users = userMetier.findUsersbyCity(ville);
			ObjectMapper mapper = new ObjectMapper(); 

			if (users != null && !users.isEmpty()) {
				tab = new JSONArray(mapper.writeValueAsString(users).toString());
				resultat.put("users", tab);
			}else{
				resultat.put("users", "");
			}
			
			resultat.put("errMess", "");  

		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service findUserbyCity() : " + e.getMessage());
		}
		return resultat.toString();

	}
	//  Gestion Role

	@RequestMapping(value="/rolebyUserId/{iduser}",method=RequestMethod.GET)
	public String findRolebyUser(@PathVariable Long iduser) throws JSONException {


		List<Role> roles = new ArrayList<>();
		JSONObject resultat = new JSONObject();
		JSONArray tab = new JSONArray();
		try { 
			roles = userMetier.findRolebyUser(iduser);
			ObjectMapper mapper = new ObjectMapper(); 

			if (roles != null && !roles.isEmpty()) {
				tab = new JSONArray(mapper.writeValueAsString(roles).toString());
				resultat.put("users", tab);
			}else{
				resultat.put("users", "");
			}
			
			resultat.put("errMess", "");  

		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service findUserbyName() : " + e.getMessage());
		}
		return resultat.toString();
	}

	@RequestMapping(value="/attribueRole/{iduser}/{idrole}",method=RequestMethod.GET)
	public String attribueRole( @PathVariable  Long iduser, @PathVariable   Long idrole) throws JSONException {


		String attribueRole = null;
		JSONObject resultat = new JSONObject();
		try {
			attribueRole =   userMetier.attribueRole(iduser, idrole);
			resultat.put("errMess", attribueRole);
		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service attribueRole() : " + e.getMessage());
		}
		return resultat.toString();
	}
	
	
	@RequestMapping(value="/retirerRole/{iduser}/{idrole}",method=RequestMethod.GET)
	public String retirerRole( @PathVariable  Long iduser, @PathVariable   Long idrole) throws JSONException {
		
		
		String RetRole = null;
		JSONObject resultat = new JSONObject();
		try {

			RetRole = userMetier.retirerRole(iduser, idrole);
			resultat.put("errMess", RetRole);
		} catch (Exception ex) {
			resultat.put("errMess", ex.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service deleteuser : " + ex.getMessage());
		}
		return resultat.toString();
	}

	
	
	@RequestMapping(value="/addrole",method=RequestMethod.POST)
	public String addRole(@RequestBody Role e) throws JSONException {
		String AddRole = null;
		JSONObject resultat = new JSONObject();
		try {

			AddRole =  userMetier.addRole(e);
			resultat.put("errMess", AddRole);
		} catch (Exception ex) {
			resultat.put("errMess", ex.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service deleteuser : " + ex.getMessage());
		}
		return resultat.toString();
	
	}
	@RequestMapping(value="/getRoles",method=RequestMethod.GET)
	public String getRoles() throws JSONException {
		List<Role> getRoles = new ArrayList<>();
		JSONObject resultat = new JSONObject();
		JSONArray tab = new JSONArray(); 
			try { 
				getRoles = userMetier.getRoles();
				ObjectMapper mapper = new ObjectMapper(); 

				if (getRoles != null && !getRoles.isEmpty()) {
					tab = new JSONArray(mapper.writeValueAsString(getRoles).toString());
					resultat.put("users", tab);
				}else{
					resultat.put("users", "tabVide");
				}
				
				resultat.put("errMess", "");  

			} catch (Exception e) {
				resultat.put("errMess", e.getMessage());
				logger.error(getClass().getName()+
						"une erreur est produite lors de l'exécution du web service findUserbyRole() : " + e.getMessage());
			}
			return resultat.toString();
	
	}
	@RequestMapping(value="/userbyRole/{idRole}",method=RequestMethod.GET)
	public String findUserbyRole(@PathVariable Long idRole) throws JSONException {
		
		List<User> users = new ArrayList<>();
		JSONObject resultat = new JSONObject();
		JSONArray tab = new JSONArray();
		
		try { 
			users = userMetier.findUsersbyRole(idRole);
			ObjectMapper mapper = new ObjectMapper(); 

			if (users != null && !users.isEmpty()) {
				tab = new JSONArray(mapper.writeValueAsString(users).toString());
				resultat.put("users", tab);
			}else{
				resultat.put("users", "");
			}
			
			resultat.put("errMess", "");  

		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service findUserbyRole() : " + e.getMessage());
		}
		return resultat.toString();
	}

	@RequestMapping(value="/deleteRole/{idRole}",method=RequestMethod.GET)
	public String deleteRole(@PathVariable Long idRole) throws JSONException {
		String delete = null;
		JSONObject resultat = new JSONObject();
		try {

			delete =  userMetier.supprimerRole(idRole);
			resultat.put("errMess", delete);
		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service delete Role : " + e.getMessage());
		}
		return resultat.toString();
	}
	
/*************************** WS pour reinsialiser lE PASSWORD  **********************/	
	@RequestMapping(value="/initEmail",method=RequestMethod.POST)
	public String InitPwdVerifierEmail(@RequestBody User user) throws JSONException {
		String init = null;
		JSONObject resultat = new JSONObject();
		try {

			init =  userMetier.InitPwdVerifierEmail(user.getEmail());
			resultat.put("errMess", init);
		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service delete Role : " + e.getMessage());
		}
		return resultat.toString();
	}
	
	
	@RequestMapping(value="/updatePwd",method=RequestMethod.POST)
	public String updatePwd(@RequestBody User user) throws JSONException {
		String init = null;
		JSONObject resultat = new JSONObject();
		try {

			init =  userMetier.updatePassword(user);
			resultat.put("errMess", init);
		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service delete Role : " + e.getMessage());
		}
		return resultat.toString();
	}
	
	
	@RequestMapping(value="/addPhotoToUser",method=RequestMethod.POST)
	public String addPhotoToUser(@RequestParam(value="file", required = false) MultipartFile file ,@RequestParam(value="json") String userJson) throws JSONException, IOException {
		
		String nomImg = "" ;
		ObjectMapper mapper = new ObjectMapper(); 
 	    User u = mapper.readValue(userJson, User.class); ;
		String Add = null;
		JSONObject resultat = new JSONObject();
		
		
		 if (file!=null) {
			  BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
			  // int randomNum = (int)(Math.random()*100); 
			  nomImg = "ImgProduit"+u.getPhoto();
			  File destination = new File("src/main/resources/imageProfil/"+nomImg+".png");
			  ImageIO.write(src, "png", destination);
			  //Save the id you have used to create the file name in the DB. You can retrieve the image in future with the ID.
			  }
		try {
            u.setPhoto(nomImg);
			Add =  userMetier.modifierUser(u);
			resultat.put("errMess", Add);
		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service adduser : " + e.getMessage());
		}
		return resultat.toString();
	
	}

}
