package com.motorcade.desktop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="t_role_resource")
public class RoleResource {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="ROLE_ID")
	private Integer roleId;
	@Column(name="RES_ID")
	private Integer resId;
	@Column(name="CREATE_TIME")
	private String createTime;
}
