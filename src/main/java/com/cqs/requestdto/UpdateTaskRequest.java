package com.cqs.requestdto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateTaskRequest implements Serializable {
	
	private static final long serialVersionUID = -7529509295435778073L;
	
	@JsonProperty("AssigneeId")
	private String assigneeId;
	@JsonProperty("Priority")
	private String priority;
	@JsonProperty("ExpectedCompletion")
	private Date expectedDate;
	@JsonProperty("Remarks")
	private String remarks;
	
}
