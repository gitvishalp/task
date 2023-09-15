package com.cqs.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import com.cqs.util.ColumnDefinition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@DynamicUpdate
@NoArgsConstructor
@Data
public class Roles implements Serializable {
	
	private static final long serialVersionUID = -5647481239254185401L;
   
	@Id
	@Column(columnDefinition = ColumnDefinition.NVARCHAR8)
    private String code;
	private String name;
	private String description;
	@Column(name = "is_active")
	private boolean active;
	@CreationTimestamp
	private Date createdAt;
	private String createdBy;
	
	public Roles(String code, String name, String description, boolean active, Date createdAt) {
		super();
		this.code = code;
		this.name = name;
		this.description = description;
		this.active = active;
		this.createdAt = createdAt;
	}
	
}
