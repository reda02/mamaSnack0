package com.mamasnack.metier;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mamasnack.entities.Categorie;
import com.mamasnack.entities.Cuisine;
//import com.mamasnack.entities.Document;
import com.mamasnack.entities.Produit;
import com.mamasnack.entities.ResponseMetadata;

public interface ProduitMetier {
	// CRUD Produit
	public String ajouterProduit(Produit p, Long IdCat) ;
	public Produit getProduit(Long idPro);
	public String supprimerProduit(Long idPro) ;
	public String modifierProduit(Produit p);
	public List<Produit> findProduitbyUser(Long iduser);
	
	// Recherche Multicritère 
	public List<Produit> listProduits();
	public List<Produit> produitsParMotCle(String mc);
	public List<Produit> listProduitsParCategorie(Long idCat) ;
	public List<Produit> listProduitsParCuisine(Long idCuisine) ;
	public List<Produit> listProduitsSelectionne() ;
	
	
	/* ResponseMetadata ajouterImage(MultipartFile multipartFile, Long idProduit) throws IOException;
     byte[] getImageFile(Long idProduit);
     List<Produit> findAll();*/
	
	// CRUD Categorie
	public String ajouterCategorie(Categorie c);
	public Categorie getCategorie(Long idC);
	public String supprimerCategorie(Long idCat) ;
	public String modifierCategorie(Categorie c);
	public List<Categorie> listCategories();
	
	// CRUD Cuisine
	public String ajouterCuisine(Cuisine c);
	public Cuisine getCuisine(Long idC);
	public String supprimerCuisine(Long idCat) ;
	public String modifierCuisine(Cuisine c);
	public List<Cuisine> listCuisines();
	public String detacheProduit(Long idPro);

}
