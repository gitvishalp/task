package com.cqs.requestdto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddTaskRequest implements Serializable {
	

	private static final long serialVersionUID = -7529509295435778073L;
	
	@JsonProperty("Title")
	private String title;
	@JsonProperty("Description")
	private String description;
	@JsonProperty("ProjectId")
	private String projectId;
	@JsonProperty("Priority")
	private String priority;
	@JsonProperty("AssigneeId")
	private String assigneeId;
	@JsonProperty("ExpectedCompletion")
	private Date expectedDate;
}
