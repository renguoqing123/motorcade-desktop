package com.motorcade.desktop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.motorcade.desktop.model.MemberRole;

@Repository
public interface MemberRoleDao extends JpaRepository<MemberRole, Integer>{

}
