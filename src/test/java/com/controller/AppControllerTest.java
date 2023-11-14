package com.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.entity.CartEntity;
import com.example.shopmsmono.Insurance_CartApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.PolicyCartRequest;
import com.model.PolicyCartResponse;
import com.service.CartService;

//@SpringBootTest(classes = Insurance_CartApplication.class)

@ExtendWith(SpringExtension.class)
//@WebMvcTest(AppController.class)
@SpringBootTest(classes = Insurance_CartApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AppControllerTest {

 @MockBean
 private CartService service;

 @Autowired
 private MockMvc mvc;

 @Test
 public void testAddPolicyToCart() throws Exception {
     // Mocking your service behavior
     List<PolicyCartRequest> policyList = Arrays.asList(
             PolicyCartRequest.builder()
                     .policyId("123")
                     .clientUsername("testUser")
                     .premium(100.0)
                     .build()
     );
     Mockito.when(service.addPolicyToCart(policyList)).thenReturn("Success");

     // Performing the test
     mvc.perform(MockMvcRequestBuilders.post("/insuranceCart/addPolicyToCart")
             .contentType(MediaType.APPLICATION_JSON)
             .content(asJsonString(policyList)))
             .andExpect(MockMvcResultMatchers.status().isOk())
             .andExpect(MockMvcResultMatchers.content().string("Success"));
 }

 @Test
 public void testGetCartItems() throws Exception {
     // Mocking your service behavior
     Integer cartId = 1;
     PolicyCartResponse response = PolicyCartResponse.builder()
             .clientUsername("testUser")
             .cartId(cartId)
             .totalPremium(200.0)
             .policyId("456")
             .build();
     Mockito.when(service.getCartItems(cartId)).thenReturn(response);

     // Performing the test
     mvc.perform(MockMvcRequestBuilders.get("/insuranceCart/getCartItems/{cartId}", cartId))
             .andExpect(MockMvcResultMatchers.status().isOk())
             .andExpect(MockMvcResultMatchers.jsonPath("$.totalPremium", Matchers.is(response.getTotalPremium())));
 }

 @Test
 public void testGetCartByClient() throws Exception {
     // Mocking your service behavior
     String clientUsername = "testUser";
     CartEntity cartEntity = CartEntity.builder()
    	        .cartId(1) // You can set the cartId if needed
    	        .clientUsername("testUser")
    	        .policyId("policy1")// Example list of policyIds
    	        .totalPremium(200.0)
    	        .build();
     Mockito.when(service.findByClientUsername(clientUsername)).thenReturn(cartEntity);

     // Performing the test
     mvc.perform(MockMvcRequestBuilders.get("/insuranceCart/getCartByUsername/{clientUsername}", clientUsername))
             .andExpect(MockMvcResultMatchers.status().isOk())
             .andExpect(MockMvcResultMatchers.jsonPath("$.clientUsername", Matchers.is(clientUsername)));
 }

 @Test
 public void testGetTotalPremium() throws Exception {
     // Mocking your service behavior
     Integer cartId = 1;
     PolicyCartResponse response = PolicyCartResponse.builder()
             .clientUsername("testUser")
             .cartId(cartId)
             .totalPremium(200.0)
             .policyId("456")
             .build();
     Mockito.when(service.getCartItems(cartId)).thenReturn(response);

     // Performing the test
     mvc.perform(MockMvcRequestBuilders.get("/insuranceCart/getTotalPremium/{cartId}", cartId))
             .andExpect(MockMvcResultMatchers.status().isOk())
             .andExpect(MockMvcResultMatchers.content().string(String.valueOf(response.getTotalPremium())));
 }

 @Test
 public void testUpdatePolicy() throws Exception {
     // Mocking your service behavior
     CartEntity cartEntity = CartEntity.builder()
 	        .cartId(1) // You can set the cartId if needed
 	        .clientUsername("testUser")
 	        .policyId("policy1")// Example list of policyIds
 	        .totalPremium(200.0)
 	        .build();
     Mockito.when(service.updatePolicyToCart(cartEntity)).thenReturn("Updated");

     // Performing the test
     mvc.perform(MockMvcRequestBuilders.post("/insuranceCart/updatePolicy")
             .contentType(MediaType.APPLICATION_JSON)
             .content(asJsonString(cartEntity)))
             .andExpect(MockMvcResultMatchers.status().isOk())
             .andExpect(MockMvcResultMatchers.content().string("Updated"));
 }

 // Utility method to convert object to JSON string
 private String asJsonString(final Object obj) {
     try {
         return new ObjectMapper().writeValueAsString(obj);
     } catch (Exception e) {
         throw new RuntimeException(e);
     }
 }
}
