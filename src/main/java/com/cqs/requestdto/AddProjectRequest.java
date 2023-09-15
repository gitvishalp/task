package com.cqs.requestdto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddProjectRequest implements Serializable {
	
	private static final long serialVersionUID = -2656474391255060826L;

	@JsonProperty("Title")
	private String title;
	@JsonProperty("Description")
	private String description;
	@JsonProperty("ExpectedCompletionDate")
	private Date completeDate;	
	
}
