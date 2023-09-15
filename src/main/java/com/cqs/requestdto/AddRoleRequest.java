package com.cqs.requestdto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddRoleRequest implements Serializable {
	
	private static final long serialVersionUID = 6621138129585166454L;

	@JsonProperty("Code")
	private String code;
	@JsonProperty("Name")
	private String name;
	@JsonProperty("Description")
	private String description;
	
}
