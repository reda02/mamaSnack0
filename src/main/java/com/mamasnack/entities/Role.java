package com.mamasnack.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
public class Role  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1348588113172381246L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idRole;
	private String roleName;
	
	@ManyToMany(mappedBy = "role")
	private List<User> User;
	
	public Long getIdRole() {
		return idRole;
	}
	public void setIdRole(Long idRole) {
		this.idRole = idRole;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Role(String roleName) {
		super();
		this.roleName = roleName;
	}
	
	public List<User> getUser() {
		return User;
	}
	@JsonIgnore
	@XmlTransient
	public void setUser(List<User> user) {
		User = user;
	}

	
	
}
