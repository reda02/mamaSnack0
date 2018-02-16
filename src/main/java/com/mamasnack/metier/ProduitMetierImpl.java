package com.mamasnack.metier;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mamasnack.dao.CategorieRepository;
import com.mamasnack.dao.CuisineRepository;
import com.mamasnack.dao.LigneCommandeRepository;
//import com.mamasnack.dao.DocumentDao;
import com.mamasnack.dao.ProduitRepository;
import com.mamasnack.entities.Categorie;
import com.mamasnack.entities.Cuisine;
import com.mamasnack.entities.LigneCommande;
//import com.mamasnack.entities.Document;
import com.mamasnack.entities.Produit;

@Service
public class ProduitMetierImpl implements ProduitMetier {

	
	@Autowired
	private ProduitRepository produitRepository ;
	@Autowired
	private CategorieRepository categorieRepository ;
	@Autowired
	private CuisineRepository cuisineRepository ;
	@Autowired
	private CommandeMetier commandeMetier ; 
	@Autowired
	private LigneCommandeRepository ligneCommandeRepository ;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public String ajouterProduit(Produit p, Long IdCat) {
		
		if (p.getIdProduit() != null && !produitRepository.existsById(p.getIdProduit())) {
				logger.error(getClass().getName()+
					    "idUser est null de l'exécution du web service addUser : ");
				return "NOK";
		}
	
		produitRepository.save(p);
		return "OK";
	}
	@Override
	public List<Produit> produitsParMotCle(String mc) {
		
		return produitRepository.findproduitParMotCle( mc);
	}

	@Override
	public Produit getProduit(Long idPro) {
		
		Produit produit= produitRepository.findOne(idPro) ;
		if (produit==null)throw new RuntimeException("produit inexistant !");
		return produit;
	}
	
	@Override
	public String detacheProduit(Long idPro) {
		    Produit produit	= getProduit(idPro) ;
		    if(Objects.nonNull(produit)){
		    produit.setCategorie(null);
			produit.setCuisine(null);
			produit.setUser(null);
			produitRepository.saveAndFlush(produit);
		    }else{
				logger.error(getClass().getName()+
					    "idUser est null de l'exécution du web service supprimerUser : ");

				return "NOK";
			}
			return "OK";
		}

	@Override
	public String supprimerProduit(Long idPro) {
		Produit produit	= getProduit(idPro) ;
		
		
		if (idPro == null ) {
			//	throw new EntityExistsException("There is already existing entity with such ID in the database.");
			
				logger.error(getClass().getName()+
					    "idUser est null de l'exécution du web service supprimerUser : ");

				return "NOK";
			}else if (produit.getIdProduit() ==null){
				
				logger.error(getClass().getName()+
					    "idProduit non existe dans BD ;erreur est produite lors de l'exécution du web service supprimerProduit : ");
				return "NOK";
			}
		
		    
			  // detacheProduit(produit);
		    if(Objects.nonNull(produit)){
		        List<LigneCommande> posts = commandeMetier.getAllLignebyIdProd(produit.getIdProduit());
		        if(posts!=null){
		        for (Iterator<LigneCommande> iterator = posts.iterator(); iterator.hasNext();) {
		        	LigneCommande post = iterator.next();
		            post.setProduit(null);
		          //iterator.remove(); //remove the child first
		            ligneCommandeRepository.save(post);
		        }
			  	
		        }
		        
		        if(produit.getPhoto1()!=null){
		        File file = new File("src/main/resources/static/images/"+produit.getPhoto1()+".png");
	    		if(file.delete()){
	    			System.out.println(file.getName() + " is deleted!");
	    		}else{
	    			System.out.println("Delete operation is failed.");
	    		}
		        }
		        
		        produitRepository.deleteByIdProd(idPro);
		    }
	             return "OK";
	}
	

	
	@Override
	public String modifierProduit(Produit p) {
		if (p.getIdProduit() != null && !produitRepository.existsById(p.getIdProduit())) {
			logger.error(getClass().getName()+
				    "une erreur est produite lors de l'exécution du web service modifierProduit : ");
			return "NOK";
		}
		produitRepository.save(p);
    	return "OK";
	}

	@Override
	public List<Produit> listProduits() {
		
		return produitRepository.findAll();
	}

	@Override
	public List<Produit> listProduitsParCategorie(Long idCat) {
		
		return produitRepository.findProduitsParCategorie(idCat);
	}

	@Override
	public List<Produit> listProduitsParCuisine(Long idCuisine) {
		
		return produitRepository.findProduitsParCuisine(idCuisine);
	}
	public List<Produit> findProduitbyUser(Long iduser) {

		return produitRepository.finProduitbyUser(iduser);
	}

	@Override
	public List<Produit> listProduitsSelectionne() {
		
		return produitRepository.findProduitsSelectionne();
	}

	@Override
	public String ajouterCategorie(Categorie c) {
		Categorie existeName = categorieRepository.findOneByName(c.getNomCategorie());
		
		if (c.getIdCategorie() != null && categorieRepository.existsById(c.getIdCategorie())) {
			throw new EntityExistsException("There is already existing entity with such ID in the database.");
		}
		  if(Objects.nonNull(existeName)){
		if (c.getNomCategorie().equals(existeName.getNomCategorie())) {

			logger.error(getClass().getName()+
				    "le nom de categorie existe deja ! ");
			return "NONOK: le nom de categorie existe deja !";
		}
		}
		
		
		categorieRepository.save(c);
		return  "OK";
	}

	@Override
	public Categorie getCategorie(Long idC) {
		Categorie categorie= categorieRepository.findOne(idC) ;
		if (categorie==null)throw new RuntimeException("categorie inexistant !");
		return categorie;
	}

	@Override
	public String supprimerCategorie(Long idCat) {
	
		Categorie categorie= getCategorie(idCat) ;
		if (idCat == null ) {
			//	throw new EntityExistsException("There is already existing entity with such ID in the database.");
			
				logger.error(getClass().getName()+
					    "id Categorie est null de l'exécution du web service supprimerCategorie : ");
				return "NOK";
			
		}else if (categorie.getIdCategorie() ==null){
				
				logger.error(getClass().getName()+
					    "id Categorie non existe dans BD ;erreur est produite lors de l'exécution du web service supprimerCategorie : ");
				return "NOK";
			}
		
		    if(Objects.nonNull(categorie)){
		        List<Produit> posts = listProduitsParCategorie(idCat);
		        for (Iterator<Produit> iterator = posts.iterator(); iterator.hasNext();) {
		            Produit post = iterator.next();
		            post.setCategorie(null);
		          //iterator.remove(); //remove the child first
		            produitRepository.save(post);
		        }
		        categorieRepository.deleteById(idCat);
		    }
		
		return "OK";
		
	}

	@Override
	public String modifierCategorie(Categorie c) {
	
		if (c.getIdCategorie() == null && !produitRepository.existsById(c.getIdCategorie())) {
			logger.error(getClass().getName()+
				    "une erreur est produite lors de l'exécution du web service modifierCategorie : ");
			return "NOK";
		}
		  
		  
		categorieRepository.save(c);
    	return "OK";
	}

	@Override
	public List<Categorie> listCategories() {
		return categorieRepository.findAll();
	}

	@Override
	public String ajouterCuisine(Cuisine c) {
		if (c.getIdCuisine() != null && cuisineRepository.existsById(c.getIdCuisine())) {
			logger.error(getClass().getName()+
				    "IdCuisine est null de l'exécution du web service ajouterCuisine : ");
			return "NOK";
		}
		cuisineRepository.save(c);
	 return "OK";
	}

	@Override
	public Cuisine getCuisine(Long idC) {
		Cuisine cuisine= cuisineRepository.getfinOne(idC) ;
		if (cuisine==null)throw new RuntimeException("cuisine inexistant !");
		return cuisine;
	}

	@Override
	public String supprimerCuisine(Long idCuis) {
		Cuisine cuisine= getCuisine(idCuis) ;
		if (idCuis == null ) {
			//	throw new EntityExistsException("There is already existing entity with such ID in the database.");
			
				logger.error(getClass().getName()+
					    "id Categorie est null de l'exécution du web service supprimerCategorie : ");
				return "NOK";
			
		}else if (cuisine.getIdCuisine()==null){
				
				logger.error(getClass().getName()+
					    "id Categorie non existe dans BD ;erreur est produite lors de l'exécution du web service supprimerCuisine : ");
				return "NOK";
			}
		 if(Objects.nonNull(cuisine)){
		        List<Produit> posts = listProduitsParCuisine(idCuis);
		        for (Iterator<Produit> iterator = posts.iterator(); iterator.hasNext();) {
		            Produit post = iterator.next();
		            post.setCuisine(null);
		           // iterator.remove(); //remove the child first
		            produitRepository.save(post);
		        }
		        cuisineRepository.deleteById(idCuis);
		    }
		
		return "OK";
		
	}

	@Override
	public String modifierCuisine(Cuisine c) {
		if (c.getIdCuisine() == null && produitRepository.existsById(c.getIdCuisine())) {
			logger.error(getClass().getName()+
				    "une erreur est produite lors de l'exécution du web service modifierCuisine : ");
			return "NOK";
		}
		cuisineRepository.save(c);
    	return "OK";
	}

	@Override
	public List<Cuisine> listCuisines() {
		return cuisineRepository.findAll();
	}


	  /*  @Override
	    public ResponseMetadata ajouterImage(MultipartFile file,Long idProduit) throws IOException {

	        Produit doc = getProduit(idProduit);
	        doc.setPhotoName(file.getOriginalFilename());
	        doc.setFile(file.getBytes());
	        produitRepository.save(doc);
	        ResponseMetadata metadata = new ResponseMetadata();
	        metadata.setMessage("success");
	        metadata.setStatus(200);
	        return metadata;
	    }

	    @Override
	    public byte[]  getImageFile(Long id) {
	      return produitRepository.findOne(id).getFile();
	    } 

	    @Override
	    public List<Produit> findAll() {
	        return (List<Produit>) produitRepository.findAll();
	    }*/

}
