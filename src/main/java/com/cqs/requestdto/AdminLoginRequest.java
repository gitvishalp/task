package com.cqs.requestdto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AdminLoginRequest implements Serializable {
	
	private static final long serialVersionUID = -8538238283006008842L;

	@JsonProperty("Email")
	private String email;
	@JsonProperty("UserName")
	private String userName;
	@JsonProperty("Password")
	private String password;
	
}
