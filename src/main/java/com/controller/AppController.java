package com.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.CartEntity;
import com.model.PolicyCartRequest;
import com.model.PolicyCartResponse;
import com.model.PolicyDeleteRequest;
import com.service.CartService;

@RestController
@RequestMapping("/insuranceCart")
@CrossOrigin("http://localhost:3002")
public class AppController {

	@Autowired
	private CartService service;

	@PostMapping("/addPolicyToCart")
	public String addPolicyToCart( @RequestBody List<PolicyCartRequest> reqPolicyList) {
		return service.addPolicyToCart(reqPolicyList);
	}

	@GetMapping("/getCartItems/{cartId}")
	public PolicyCartResponse getCartItems(@PathVariable Integer cartId) {

		return service.getCartItems(cartId);
	}
	
	@GetMapping("/getCartByUsername/{clientUsername}")
	public CartEntity getCartByClient(@PathVariable String clientUsername) {
		CartEntity cart = service.findByClientUsername(clientUsername);
		return cart;
	}

	@GetMapping("/getTotalPremium/{cartId}")
	public Double getTotalPremium(@PathVariable Integer cartId) {
		return getCartItems(cartId).getTotalPremium();
	}

	@PostMapping("/updatePolicy")
	public String updatePolicy(@RequestBody CartEntity entity) {
		return service.updatePolicyToCart(entity);
	}
	
	@DeleteMapping("/deleteCart/{clientUsername}")
	@Transactional
	public String deleteCart(@PathVariable String clientUsername) {
		return service.deleteCartByUsername(clientUsername);
	}
	
	@PostMapping("/delete")
	@Transactional
	@CrossOrigin
	public String deletebypolicyId(@RequestBody PolicyDeleteRequest deleterequest)
	{
		return service.deleteCartById(deleterequest);
	}
	
}
