package com.cqs.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import com.cqs.util.ColumnDefinition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table
@DynamicUpdate
@NoArgsConstructor
public class Task implements Serializable {

	private static final long serialVersionUID = 1541275799820209427L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String title;
	private String description;
	@ManyToOne
	private Project project;
	@ManyToOne
	private Priority priority;
	@ManyToOne
	private Employee assignee;
	private String status;
	@Column(columnDefinition = ColumnDefinition.BIT)
	private boolean active;
	@CreationTimestamp
	private Date createdAt;
	private Date expectedCompletionDate;
	private Date actualCompletion;
	private String remarks;
	private String createdby;
	private String updatedBy;
	
	public Task(String id, String title, String description, Priority priority, Employee assignee, String status,
			boolean active, Date createdAt, Date expectedCompletionDate, String createdby) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.assignee = assignee;
		this.status = status;
		this.active = active;
		this.createdAt = createdAt;
		this.expectedCompletionDate = expectedCompletionDate;
		this.createdby = createdby;
	}
}
