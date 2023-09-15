package com.cqs.requestdto;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddEmployeeRequest implements Serializable {
	
	private static final long serialVersionUID = 6603779603411707571L;

	@JsonProperty("Name")
	private String name;
	@JsonProperty("Designition")
	private String designition;
	@JsonProperty("DOB")
	private Date dateofbirth;
	@JsonProperty("Email")
	private String email;
	@JsonProperty("PhoneNumber")
	private String phoneNumber;
	@JsonProperty("Role")
	private String role;
	
	public AddEmployeeRequest(String name, String designition, Date dateofbirth, String email, String phoneNumber,
			String role) {
		super();
		this.name = name;
		this.designition = designition;
		this.dateofbirth = dateofbirth;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}
	
}
