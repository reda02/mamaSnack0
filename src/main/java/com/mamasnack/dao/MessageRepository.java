package com.mamasnack.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mamasnack.entities.Message;

 

 public interface MessageRepository extends JpaRepository<Message, Long>{ 
	
	
}