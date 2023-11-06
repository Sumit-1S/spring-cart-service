package com.service;

import java.util.List;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.CartEntity;
import com.model.Policy;
import com.model.PolicyCartRequest;
import com.model.PolicyCartResponse;

public interface CartService {
 public String addPolicyToCart(@ModelAttribute List<PolicyCartRequest> requestPolicy);
 public String updatePolicyToCart(@ModelAttribute CartEntity reqCartUpdateRequest);
// public Double TotalPremium(@ModelAttribute CartEntity requestCart);
 public PolicyCartResponse getCartItems(@RequestBody Integer cartId);
 public String getPolicybyId();
 public CartEntity findByClientUsername(String clientUsername);
}
