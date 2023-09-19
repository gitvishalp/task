package com.cqs.responsedto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeLoginResponse implements Serializable {

	 private static final long serialVersionUID = -4199741487579742670L;
	  
	  
	  @JsonProperty("Token")
	  private String token;
	  @JsonProperty("FirstLogin")
	  private boolean firstLogin;
	  @JsonProperty("Time")
	  private Date date;	
	  
}
