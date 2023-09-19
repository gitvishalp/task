package com.cqs.requestdto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EmployeeLogin implements Serializable{

	private static final long serialVersionUID = -2295963113163896907L;
	@JsonProperty("Email")
	private String email;
	@JsonProperty("Password")
	private String password;
	
}
