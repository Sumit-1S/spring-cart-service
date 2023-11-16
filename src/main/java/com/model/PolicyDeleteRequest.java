package com.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class PolicyDeleteRequest {
	public Integer policyId;
	public Integer premium;
	public String clientUsername;
}
