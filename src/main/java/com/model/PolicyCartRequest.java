package com.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PolicyCartRequest {
	
	private String policyId;
	private String clientUsername; 
	private Double premium;
	
}
