package com.cqs.requestdto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePasswordRequest implements Serializable {
	

	private static final long serialVersionUID = -6070669089536775955L;
	@JsonProperty("NewPass")
	private String newPass;
}
