package com.motorcade.desktop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.motorcade.desktop.model.Member;

@Repository
public interface MemberDao extends JpaRepository<Member, Integer>{

}
