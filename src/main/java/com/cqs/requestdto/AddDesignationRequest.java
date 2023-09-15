package com.cqs.requestdto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddDesignationRequest implements Serializable {

	private static final long serialVersionUID = 6370139670826805759L;
	
	@JsonProperty("Code")
	private String code;
	@JsonProperty("Name")
	private String name;
	@JsonProperty("Description")
	private String description;
	
}
