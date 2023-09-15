package com.cqs.entity;

import java.io.Serializable;
import org.hibernate.annotations.DynamicUpdate;

import com.cqs.util.ColumnDefinition;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
@Table
@Entity
@Data
@NoArgsConstructor
@DynamicUpdate
public class Priority implements Serializable {
	
	private static final long serialVersionUID = 4684226319506334000L;

	@Id
	@Column(columnDefinition = ColumnDefinition.NVARCHAR8)
    private String code;
	private String name;
	private String description;
	
	public Priority(String code, String name, String description) {
		super();
		this.code = code;
		this.name = name;
		this.description = description;
	}
	
}
