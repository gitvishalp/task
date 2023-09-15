package com.cqs.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table
@DynamicUpdate
@Data
public class Admin implements Serializable{
	
	private static final long serialVersionUID = 7110288020045318531L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String userName;
    private String email;
    private String password;
    @ManyToOne
    private Roles role;
    @CreationTimestamp
    private Date createdAt;
    
	public Admin(String id, String userName, String email, String password, Roles role, Date createdAt) {
		super();
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.role = role;
		this.createdAt = createdAt;
	}
	
}
