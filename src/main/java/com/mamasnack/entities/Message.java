package com.mamasnack.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Message  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -932642278553084089L;
	@Id
	private Long idMsg;
	private String distinataire;
	private String contenuMesg;
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date dateEnregistrement;

	@ManyToMany(mappedBy = "message")
	private List<User> User;
	
	
	public Long getIdMsg() {
		return idMsg;
	}
	public void setIdMsg(Long idMsg) {
		this.idMsg = idMsg;
	}
	public String getDistinataire() {
		return distinataire;
	}
	public void setDistinataire(String distinataire) {
		this.distinataire = distinataire;
	}
	public String getContenuMesg() {
		return contenuMesg;
	}
	public void setContenuMesg(String contenuMesg) {
		this.contenuMesg = contenuMesg;
	}
	public Date getDateEnregistrement() {
		return dateEnregistrement;
	}
	public void setDateEnregistrement(Date dateEnregistrement) {
		this.dateEnregistrement = dateEnregistrement;
	}
	
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Message(String distinataire, String contenuMesg, Date dateEnregistrement) {
		super();
		this.distinataire = distinataire;
		this.contenuMesg = contenuMesg;
		this.dateEnregistrement = dateEnregistrement;
	}
	
}
