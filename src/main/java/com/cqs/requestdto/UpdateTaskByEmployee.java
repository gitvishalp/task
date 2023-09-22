package com.cqs.requestdto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateTaskByEmployee implements Serializable{

	private static final long serialVersionUID = -8168469831825022098L;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("Remarks")
	private String remarks;
}
