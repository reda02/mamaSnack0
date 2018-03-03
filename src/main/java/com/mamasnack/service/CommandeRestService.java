package com.mamasnack.service;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CommandeRestService  {
	
	@RequestMapping(value="/listCommandes",method=RequestMethod.GET)
	public String listCommandes()  {
		
		return null;
	}
}