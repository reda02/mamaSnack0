package com.mamasnack.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonInclude
@Entity
public class Commentaire {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idCommentaire;
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date dateCommentaire;
	private String message ;
	private String note ;
	
	@ManyToOne
	@JoinColumn(name = "idmama", referencedColumnName = "idUser", nullable = false)
	private User user ;

	public Long getIdCommentaire() {
		return idCommentaire;
	}

	public void setIdCommentaire(Long idCommentaire) {
		this.idCommentaire = idCommentaire;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Commentaire() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Commentaire(String message, String note, User user) {
		super();
		this.message = message;
		this.note = note;
		this.user = user;
	}
	
	public Date getDateCommentaire() {
		return dateCommentaire;
	}

	public void setDateCommentaire(Date dateCommentaire) {
		this.dateCommentaire = dateCommentaire;
	}
	
}
