package com.motorcade.desktop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.motorcade.desktop.model.Resource;

@Repository
public interface ResourceDao extends JpaRepository<Resource, Integer>{
	
}
