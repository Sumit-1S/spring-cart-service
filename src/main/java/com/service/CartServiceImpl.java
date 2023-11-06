package com.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;

import com.entity.CartEntity;

import com.model.PolicyCartRequest;
import com.model.PolicyCartResponse;
import com.repo.InsuranceRepo;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	@Qualifier("webclient")
	private WebClient.Builder builder;

	@Autowired
	InsuranceRepo insuranceRepo;

	@Override
	public String addPolicyToCart(@ModelAttribute List<PolicyCartRequest> requestPolicy) {

		Double totalpremium = 0.0;
		for (PolicyCartRequest p : requestPolicy) {
			totalpremium += p.getPremium();
		}
		// create cart entity
		CartEntity cartEntity = null;
		try {
			
			cartEntity = CartEntity.builder()
					.clientUsername(requestPolicy.get(0).getClientUsername())
					.policyId(requestPolicy.stream().map(m -> m.getPolicyId().toString()).collect(Collectors.joining(",")))
					.totalPremium(totalpremium).build();
			cartEntity = insuranceRepo.save(cartEntity);
			
		} catch (Exception e) {

		}

		return " Item Added";
	}



	@Override
	public PolicyCartResponse getCartItems(@RequestBody Integer cartId) {
		// TODO Auto-generated method stub
		CartEntity temp = insuranceRepo.findById(cartId).get();

		return PolicyCartResponse.builder()
				.cartId(temp.getCartId())
				.totalPremium(temp.getTotalPremium())
				.policyId(temp.getPolicyId())
				.clientUsername(temp.getClientUsername())
				.build();
	}

	@Override
	public String getPolicybyId() {

		return null;
	}

	@Override
	public String updatePolicyToCart(@ModelAttribute CartEntity reqCartUpdateRequest) {
		// TODO Auto-generated method stub
		insuranceRepo.save(reqCartUpdateRequest);
		return "Updated";
	}
	@Override
	public CartEntity findByClientUsername(String clientUsername) {
		return insuranceRepo.findByClientUsername(clientUsername);
	}

}
