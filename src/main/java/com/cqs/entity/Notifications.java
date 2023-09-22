package com.cqs.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table
@DynamicUpdate
@NoArgsConstructor
public class Notifications implements Serializable{
	
	private static final long serialVersionUID = 1541275799820209427L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String title;
	private String message;
	private String type;
	private String toId;
	private Date createdAt;
}
