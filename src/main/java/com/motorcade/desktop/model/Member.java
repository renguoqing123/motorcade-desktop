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
@Table(name="t_member")
public class Member {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="USER_NAME")
	private String userName;
	@Column(name="PASSWORD")
	private String password;
	@Column(name="PASSWORD_CLEAR_TEXT")
	private String passwordClearText;
	@Column(name="REAL_NAME")
	private String realName;
	@Column(name="MOBILE")
	private String mobile;
	@Column(name="EMAIL")
	private String email;
	@Column(name="MEMBER_TYPE")
	private String memberType;
	@Column(name="LAST_LOGIN_TIME")
	private String lastLoginTime;
	@Column(name="CREATE_TIME")
	private String createTime;
	@Column(name="CREATE_USER")
	private String createUser;
	
}
