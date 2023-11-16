package com.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@Builder

public class PolicyCartRequest {
	
	private String policyId;
	private String clientUsername; 
	private Double premium;
	
	
	
}
