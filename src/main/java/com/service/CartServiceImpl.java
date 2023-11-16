package com.service;

import java.util.Arrays;
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
import com.model.PolicyDeleteRequest;
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
		String policyIds = "";
		CartEntity cart = insuranceRepo.findByClientUsername(requestPolicy.get(0).getClientUsername());
		if(cart!=null) {
			for(PolicyCartRequest req:requestPolicy) {
				if(!Arrays.stream(cart.getPolicyId().split(",")).anyMatch(value->value==req.getPolicyId())) {
					totalpremium+=req.getPremium();
					policyIds=req.getPolicyId()+","+policyIds;
				}				
			}
			cart.setPolicyId(policyIds+cart.getPolicyId());
			cart.setTotalPremium(totalpremium+cart.getTotalPremium());
			return updatePolicyToCart(cart);
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
	
	@Override
	public String deleteCartByUsername(String clientUsername) {
		return insuranceRepo.deleteByClientUsername(clientUsername);
		
	}



	@Override
	public String deleteCartById(@ModelAttribute PolicyDeleteRequest deleterequest) {
		// TODO Auto-generated method stub
		System.out.println(deleterequest.getClientUsername());
		CartEntity cart =insuranceRepo.findByClientUsername(deleterequest.getClientUsername());
		String policyIdcart=cart.getPolicyId();
		policyIdcart = Arrays.stream(policyIdcart.split(","))

                .filter(s -> !s.equals(String.valueOf(deleterequest.getPolicyId())))

                .collect(Collectors.joining(","));
		cart.setPolicyId(policyIdcart);
		cart.setTotalPremium(cart.getTotalPremium() - deleterequest.getPremium());
		updatePolicyToCart(cart);
		return "Deleted";
	}
	
	

}
