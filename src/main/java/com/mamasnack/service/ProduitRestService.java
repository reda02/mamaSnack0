package com.mamasnack.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mamasnack.entities.Categorie;
import com.mamasnack.entities.Cuisine;
//import com.mamasnack.entities.Document;
import com.mamasnack.entities.Produit;
import com.mamasnack.metier.ProduitMetier;

@RestController
public class ProduitRestService {
	@Autowired
	private ProduitMetier produitMetier ;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	
	
	@RequestMapping(value="/addProduit",produces = "application/json",method=RequestMethod.POST)
	public @ResponseBody String ajouterProduit(@RequestParam(value="files", required = false) MultipartFile[] files,
			
	@RequestParam(value="json") String json, Long IdCat) throws JSONException, JsonParseException, JsonMappingException, IOException {    			
		/*for (Map<String, MultipartFile> map : file) {
		    for (Map.Entry<String, MultipartFile> entry : map.entrySet()) {		       
		        System.out.println(entry.getKey() + " - " + entry.getValue());
		    }
		}*/
		ObjectMapper mapper = new ObjectMapper();
		Produit p = mapper.readValue(json, Produit.class); 
		/*@RequestParam(value="file2", required = false) MultipartFile file2,
		@RequestParam(value="file3", required = false) MultipartFile file3,
		@RequestParam(value="file4", required = false) MultipartFile file4,
		@RequestParam(value="file5", required = false) MultipartFile file5,   
		String nomImg = "" ;
		   String nomImg2 = "" ;
		   String nomImg3 = "" ;
		   String nomImg4 = "" ;
		   String nomImg5 = "" ;
		   ObjectMapper mapper = new ObjectMapper(); 
    	   Produit p = mapper.readValue(json, Produit.class); ;
    	  if (file!=null) {
			  BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
			  nomImg = "ImgProduit"+p.getPhoto1();
			  File destination = new File("src/main/resources/static/images/"+nomImg+".png");// something like C:/Users/tom/Documents/nameBasedOnSomeId.png
			  ImageIO.write(src, "png", destination);
			  }
    	  if (file2!=null) {
			  BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
			  nomImg2 = "ImgProduit"+p.getPhoto2();
			  File destination = new File("src/main/resources/static/images/"+nomImg+".png");// something like C:/Users/tom/Documents/nameBasedOnSomeId.png
			  ImageIO.write(src, "png", destination);
			  }
    	  if (file3!=null) {
			  BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
			  nomImg3 = "ImgProduit"+p.getPhoto3();
			  File destination = new File("src/main/resources/static/images/"+nomImg+".png");// something like C:/Users/tom/Documents/nameBasedOnSomeId.png
			  ImageIO.write(src, "png", destination);
			  }
    	  if (file4!=null) {
			  BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
			  nomImg4 = "ImgProduit"+p.getPhoto4();
			  File destination = new File("src/main/resources/static/images/"+nomImg+".png");// something like C:/Users/tom/Documents/nameBasedOnSomeId.png
			  ImageIO.write(src, "png", destination);
			  }
    	  if (file5!=null) {
			  BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
			  nomImg5 = "ImgProduit"+p.getPhoto4();
			  File destination = new File("src/main/resources/static/images/"+nomImg+".png");// something like C:/Users/tom/Documents/nameBasedOnSomeId.png
			  ImageIO.write(src, "png", destination);
			  } */
		String Add = null;
		JSONObject resultat = new JSONObject();
		try {
			/*p.setPhoto1(nomImg);
			p.setPhoto2(nomImg2);
			p.setPhoto3(nomImg3);
			p.setPhoto4(nomImg4);
			p.setPhoto5(nomImg5);*/
			Add =  produitMetier.ajouterProduit(p, IdCat);
			resultat.put("errMess", Add);
		    } catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service ajouterProduit : " + e.getMessage());
		}
		return resultat.toString();
	}
	

	@RequestMapping(value="/getProduit/{idPro}",method=RequestMethod.GET)
	public String getProduit(@PathVariable Long idPro) throws JSONException {
		

		Produit produit = null;
		JSONObject resultat = new JSONObject();
		try {
			produit = produitMetier.getProduit(idPro);
			ObjectMapper mapper = new ObjectMapper(); 

			resultat.put("produit", new  JSONObject(mapper.writeValueAsString(produit).toString()));
			resultat.put("errMess", "");
		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service getProduit : " + e.getMessage());
		}
		return resultat.toString();
	}

	@RequestMapping(value="/deleteProduit/{idPro}",method=RequestMethod.GET)
	public String supprimerProduit(@PathVariable Long idPro) throws JSONException {
		String delete = null;
		JSONObject resultat = new JSONObject();
		try {
			
			delete =  produitMetier.supprimerProduit(idPro);
			resultat.put("errMess", delete);
		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service deleteProduit : " + e.getMessage());
		}
		return resultat.toString();
	}
	
	@RequestMapping(value="/deletachProduit/{idPro}",method=RequestMethod.GET)
	public String deletach(@PathVariable Long idPro) throws JSONException {
		String delete = null;
		JSONObject resultat = new JSONObject();
		try {
			
			delete = produitMetier.detacheProduit(idPro);
			resultat.put("errMess", delete);
		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service deleteProduit : " + e.getMessage());
		}
		return resultat.toString();
	}

	
	   @RequestMapping(value="/updateProduit",method=RequestMethod.POST)
		public @ResponseBody String modifierProduit(@RequestParam(value="file", required = false) MultipartFile file,
	    		@RequestParam(value="json") String json) throws JSONException, JsonParseException, JsonMappingException, IOException {
			String nomImg = null ;
			ObjectMapper mapper = new ObjectMapper(); 
	    	Produit p = mapper.readValue(json, Produit.class); ;
	    	if (file!=null) {
				  BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
				  int randomNum = (int)(Math.random()*100); 
				   nomImg = "ImgProduit"+randomNum ;
				  File destination = new File("src/main/resources/static/images/"+nomImg+".png");// something like C:/Users/tom/Documents/nameBasedOnSomeId.png
				  ImageIO.write(src, "png", destination);
				  //Save the id you have used to create the file name in the DB. You can retrieve the image in future with the ID.
		     }
		
		String update = null;
		JSONObject resultat = new JSONObject();
		try {
			update = produitMetier.modifierProduit(p);
			resultat.put("updateMess", update);
			resultat.put("errMess", "");
		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service updateuser : " + e.getMessage());
		}
		return resultat.toString();
	}

	@RequestMapping(value="/getProduits",method=RequestMethod.GET)
	public String listProduits() throws JSONException {

		List<Produit> produits = new ArrayList<>();
		JSONObject resultat = new JSONObject();
		JSONArray tab = new JSONArray();
		try { 
			produits = produitMetier.listProduits();
			ObjectMapper mapper = new ObjectMapper(); 
			resultat.put("errMess", "");
			if (produits != null && !produits.isEmpty()) {
				tab = new JSONArray(mapper.writeValueAsString(produits).toString());
				resultat.put("produits", tab);
			}else{
				resultat.put("produits", "");
			}
			
			  

		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service getProduits() : " + e.getMessage());
		}
		return resultat.toString();

	}
    //toDo
	@RequestMapping(value="/getProduitsParCat/{idCat}",method=RequestMethod.GET)
	public String listProduitsParCategorie(@PathVariable Long idCat) throws JSONException {
	
		
		List<Produit> produits = new ArrayList<>();
		JSONObject resultat = new JSONObject();
		JSONArray tab = new JSONArray();
		try { 
			produits = produitMetier.listProduitsParCategorie(idCat);
			ObjectMapper mapper = new ObjectMapper(); 
			resultat.put("errMess", "");
			if (produits != null && !produits.isEmpty()) {
				tab = new JSONArray(mapper.writeValueAsString(produits).toString());
				resultat.put("produits", tab);
			}else{
				resultat.put("produits", "");
			}  

		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service getProduitsParCat() : " + e.getMessage());
		}
		return resultat.toString();
	}
	
	@RequestMapping(value="/getProduitsBykeyword/{keyword}",method=RequestMethod.GET )
        public String produitsParMotCle(@PathVariable String keyword) throws JSONException {

		List<Produit> produits = new ArrayList<>();
		JSONObject resultat = new JSONObject();
		JSONArray tab = new JSONArray();
		try { 
			produits = produitMetier.produitsParMotCle(keyword);
			ObjectMapper mapper = new ObjectMapper(); 
			resultat.put("errMess", "");
			if (produits != null && !produits.isEmpty()) {
				tab = new JSONArray(mapper.writeValueAsString(produits).toString());
				resultat.put("produits", tab);
			}else{
				resultat.put("produits", "");
			}  

		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service getProduitsBykeyword() : " + e.getMessage());
		}
		return resultat.toString();
	}
	@RequestMapping(value="/produitsByUser/{iduser}",method=RequestMethod.GET)
	public String findProduitbyUser(@PathVariable Long iduser) throws JSONException {
		
		List<Produit> produits = new ArrayList<>();
		JSONObject resultat = new JSONObject();
		JSONArray tab = new JSONArray();
		try { 
			produits = produitMetier.findProduitbyUser(iduser);
			ObjectMapper mapper = new ObjectMapper(); 

			if (produits != null && !produits.isEmpty()) {
				tab = new JSONArray(mapper.writeValueAsString(produits).toString());
				resultat.put("produits", tab);
			}else{
				resultat.put("produits", "");
			}
			
			resultat.put("errMess", "");  

		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service findProduitbyUser() : " + e.getMessage());
		}
		return resultat.toString();
	}
	
	
//toDo
	@RequestMapping(value="/getProduitsParCuisine/{idCuisine}",method=RequestMethod.GET)
	public String listProduitsParCuisine(@PathVariable Long idCuisine) throws JSONException {

		List<Produit> produits = new ArrayList<>();
		JSONObject resultat = new JSONObject();
		JSONArray tab = new JSONArray();
		try { 
			produits = produitMetier.listProduitsParCuisine(idCuisine);
			ObjectMapper mapper = new ObjectMapper(); 
			resultat.put("errMess", "");
			if (produits != null && !produits.isEmpty()) {
				tab = new JSONArray(mapper.writeValueAsString(produits).toString());
				resultat.put("produits", tab);
			}else{
				resultat.put("produits", "");
			}  

		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service getProduitsParCuisine() : " + e.getMessage());
		}
		return resultat.toString();
	}

	@RequestMapping(value="/listProduitsSelectionne",method=RequestMethod.GET)
	public String listProduitsSelectionne() throws JSONException {
	
		List<Produit> produits = new ArrayList<>();
		JSONObject resultat = new JSONObject();
		JSONArray tab = new JSONArray();
		try { 
			produits = produitMetier.listProduitsSelectionne();
			ObjectMapper mapper = new ObjectMapper(); 
			resultat.put("errMess", "");
			if (produits != null && !produits.isEmpty()) {
				tab = new JSONArray(mapper.writeValueAsString(produits).toString());
				resultat.put("produits", tab);
			}else{
				resultat.put("produits", "");
			}  

		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service listProduitsSelectionne() : " + e.getMessage());
		}
		return resultat.toString();
		
	}

	@RequestMapping(value="/addCat",method=RequestMethod.POST)
	public String ajouterCategorie(@RequestBody Categorie c) throws JSONException {
	
		
		String Addc = null;
		JSONObject resultat = new JSONObject();
		try {
			Addc =   produitMetier.ajouterCategorie(c);;
			resultat.put("errMess", Addc);
		    } catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service ajouterCategorie : " + e.getMessage());
		}
		return resultat.toString();
	}

	@RequestMapping(value="/getCat/{idC}",method=RequestMethod.GET)
	public String getCategorie(@PathVariable Long idC) throws JSONException {
		
		
		
		Categorie cate = null;
		JSONObject resultat = new JSONObject();
		try {
			cate = produitMetier.getCategorie(idC);;
			ObjectMapper mapper = new ObjectMapper(); 

			resultat.put("categorie", new  JSONObject(mapper.writeValueAsString(cate).toString()));
			resultat.put("errMess", "");
		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service getCategorie : " + e.getMessage());
		}
		return resultat.toString();
	}

	@RequestMapping(value="/deleteCat/{idCat}",method=RequestMethod.GET)
	public String supprimerCategorie(@PathVariable Long idCat) throws JSONException {
		String supp=null;
		JSONObject resultat = new JSONObject();
		try {
			supp =  produitMetier.supprimerCategorie(idCat);
			resultat.put("errMess", supp);
		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service SupprimerCategorie : " + e.getMessage());
		}
		return resultat.toString();
		
	}

	@RequestMapping(value="/updateCat",method=RequestMethod.POST)
	public String modifierCategorie(@RequestBody Categorie c) throws JSONException {
		String update = null;
		JSONObject resultat = new JSONObject();
		try {
			update= produitMetier.modifierCategorie(c);
			resultat.put("updateMess", update);
			resultat.put("errMess", "");
		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service updateCategorie : " + e.getMessage());
		}
		return resultat.toString();
	}

	@RequestMapping(value="/getCats",method=RequestMethod.GET)
	public List<Categorie> listCategories() {
		
		return produitMetier.listCategories();
	}

	@RequestMapping(value="/addCuisines",method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String ajouterCuisine(@RequestBody Cuisine c) throws JSONException {
	
		String Addc = null;
		JSONObject resultat = new JSONObject();
		try {
			Addc =   produitMetier.ajouterCuisine(c);
			resultat.put("errMess", Addc);
		    } catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service ajouterProduit : " + e.getMessage());
		}
		return resultat.toString();
	}

	
	// not working 
	@RequestMapping(value="/getCuisine/{idC}",method=RequestMethod.GET)
	public String getCuisine(@PathVariable Long idC) throws JSONException {
		
		Cuisine cuisine = null;
		JSONObject resultat = new JSONObject();
		try {
			cuisine = produitMetier.getCuisine(idC);
			ObjectMapper mapper = new ObjectMapper(); 

			resultat.put("cuisine", new  JSONObject(mapper.writeValueAsString(cuisine).toString()));
			resultat.put("errMess", "");
		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service getCuisine : " + e.getMessage());
		}
		return resultat.toString();
	}

	@RequestMapping(value="/deleteCuisine/{idC}",method=RequestMethod.GET)
	public String supprimerCuisine(@PathVariable Long idC) throws JSONException {
		
		String supp=null;
		JSONObject resultat = new JSONObject();
		try {
			supp =  produitMetier.supprimerCuisine(idC);
			resultat.put("errMess", supp);
		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service supprimerCuisine : " + e.getMessage());
		}
		return resultat.toString();
		
	}

	@RequestMapping(value="/updateCuisine",method=RequestMethod.POST)
	public String modifierCuisine(@RequestBody Cuisine c) throws JSONException {
		
		String update = null;
		JSONObject resultat = new JSONObject();
		try {
			update= produitMetier.modifierCuisine(c);
			resultat.put("updateMess", update);
			resultat.put("errMess", "");
		} catch (Exception e) {
			resultat.put("errMess", e.getMessage());
			logger.error(getClass().getName()+
					"une erreur est produite lors de l'exécution du web service updateCuisine : " + e.getMessage());
		}
		return resultat.toString();
		
	}

	@RequestMapping(value="/getAllCuisines",method=RequestMethod.GET)
	public String listCuisines() {
		return toJSON(produitMetier.listCuisines());
	}

	   /* @RequestMapping(value = "/upload/{idProduit}", method = RequestMethod.POST)
	    public @ResponseBody ResponseMetadata handleFileUpload(@RequestParam(value="file") MultipartFile file,@PathVariable Long idProduit) throws IOException {
	        //return produitMetier.ajouterImage(file,idProduit);
		  
		  
	    }
     /*
	    @RequestMapping(value = "/getImagebyProd/{idProduit}", method = RequestMethod.GET)
	    public HttpEntity<byte[]> getImage(@PathVariable Long idProduit) {
	        HttpHeaders httpHeaders = new HttpHeaders();
	        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
	        return new ResponseEntity<byte[]>(produitMetier.getImageFile(idProduit), httpHeaders, HttpStatus.OK);
	    }

	    @RequestMapping(value = "/getAllImagByProd/{idProduit}",method = RequestMethod.GET)
	    public @ResponseBody List<Produit> getAllImagByProd(@PathVariable Long idProduit) {
	        return produitMetier.findAll();
	    }*/
	
	public static String toJSON(Object object) 
    { 
        if ( object == null ){
        return "l'objet n'existe pas"; 
        } 
        try { 
           ObjectMapper mapper = new ObjectMapper(); 
           return mapper.writeValueAsString(object); 
           } 
        catch (Exception e) { 
         e.printStackTrace(); 
        } 
      return "{}"; 
      
}
}