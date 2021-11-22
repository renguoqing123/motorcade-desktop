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
@Table(name="t_resource")
public class Resource {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="RES_NAME")
	private String resName;
	@Column(name="PARENT_ID")
	private Integer parentId;
	@Column(name="PARENT_RES_NAME")
	private String parentResName;
	@Column(name="RES_TYPE")
	private Integer resType;
	@Column(name="URL")
	private String url;
	@Column(name="SORT_INDEX")
	private Integer sortIndex;
	@Column(name="CREATE_TIME")
	private String createTime;
}
