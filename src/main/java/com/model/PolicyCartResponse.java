package com.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class PolicyCartResponse {
	private String clientUsername;
	private Integer cartId;
	private Double totalPremium;
	private String policyId;
}
