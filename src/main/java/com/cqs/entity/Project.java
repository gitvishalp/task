package com.cqs.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
@DynamicUpdate
@Data

public class Project implements Serializable {

	
	private static final long serialVersionUID = -8316966835130024037L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String projectName;
	private String description;
	private String status;
	@Column(columnDefinition = "INT DEFAULT 0")
	private int activeTasks;
	@CreationTimestamp
	private Date createdAt;
	private Date expectedCompletion;
}
