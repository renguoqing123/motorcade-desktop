package com.motorcade.desktop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.motorcade.desktop.model.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, Integer>{

}
