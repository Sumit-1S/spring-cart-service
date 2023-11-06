package com.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Policy {	
	private Integer policyId;
	private String policyName;
	private String policyDesc;
	private Integer duration;
	private Double premium;
	private PolicyType policyType;
}
