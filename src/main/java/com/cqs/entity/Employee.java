package com.cqs.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import com.cqs.util.ColumnDefinition;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@DynamicUpdate
@Table
@Entity
@NoArgsConstructor
public class Employee implements Serializable {
	
	private static final long serialVersionUID = 9083169325385773959L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String name;
	private LocalDate dateOfBirth;
	@ManyToOne
	private Roles role;
	@ManyToOne
	private Designition designition;
	private String email;
	private String phoneNumber;
	private String password;
	@Lob
	private byte[] profile;
	@Column(columnDefinition = "INT DEFAULT 0")
	private int activeTasks;
	@Column(columnDefinition = ColumnDefinition.BIT)
	private boolean isActive;
	@Column(columnDefinition = ColumnDefinition.BIT)
	private boolean firstLogin;
	@CreationTimestamp
	private Date createdAt;
	private String createdBy;
	
	public Employee(String name, Designition desig, LocalDate dateOfBirth, Roles role, String email,
			String phoneNumber, String password, boolean isActive,
			Date createdAt) {
		super();
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.role = role;
		this.designition= desig;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.isActive = isActive;
		this.createdAt = createdAt;
	}
}
