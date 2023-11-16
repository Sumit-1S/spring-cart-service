package com.controller;

import com.entity.CartEntity;
//import com.controller.AppController;
//import com.entity.CartEntity;
//import com.model.PolicyCartRequest;
//import com.model.PolicyCartResponse;
//import com.service.CartService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Arrays;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(MockitoExtension.class)
//public class AppControllerTest {
//
//    @InjectMocks
//    private AppController appController;
//
//    @Mock
//    private CartService cartService;
//
//    private MockMvc mockMvc;
//
//    @Test
//    public void testAddPolicyToCart() throws Exception {
//        // Create a sample requestPolicy list
//        PolicyCartRequest request = PolicyCartRequest.builder()
//                .policyId("123")
//                .clientUsername("User123")
//                .premium(1000.0)
//                .build();
//
//        // Create a sample response from the service
//        when(cartService.addPolicyToCart(Arrays.asList(request))).thenReturn("Item Added");
//
//        // Initialize MockMvc using standalone setup
//        mockMvc = MockMvcBuilders.standaloneSetup(appController).build();
//
//        // Perform the HTTP request and verify the response
//        mockMvc.perform(post("/insuranceCart/addPolicyToCart")
//                .contentType("application/json")
//                .content("{\"policyId\":\"123\",\"clientUsername\":\"User123\",\"premium\":1000.0}"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Item Added"));
//    }
//
//    @Test
//    public void testGetCartItems() throws Exception {
//        // Create a sample response from the service
//        PolicyCartResponse expectedResponse = PolicyCartResponse.builder()
//                .cartId(1)
//                .totalPremium(1000.0)
//                .policyId("123")
//                .clientUsername("TestUser")
//                .build();
//
//        when(cartService.getCartItems(1)).thenReturn(expectedResponse);
//
//        // Initialize MockMvc using standalone setup
//        mockMvc = MockMvcBuilders.standaloneSetup(appController).build();
//
//        // Perform the HTTP request and verify the response
//        mockMvc.perform(get("/insuranceCart/getCartItems/{cartId}", 1))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.cartId").value(1))
//                .andExpect(jsonPath("$.totalPremium").value(1000.0))
//                .andExpect(jsonPath("$.policyId").value("123"))
//                .andExpect(jsonPath("$.clientUsername").value("TestUser"));
//    }
//
//    // Add more tests for other controller methods as needed
//}
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.PolicyCartRequest;
import com.model.PolicyCartResponse;
import com.service.CartService;
import com.service.CartServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AppControllerTest {

    @InjectMocks
    private AppController appController;

    @Mock
    private CartService cartService;

    private MockMvc mockMvc;
 
    @Test
    public void testGetCartItems() throws Exception {
        // Create a sample response from the service
        PolicyCartResponse expectedResponse = PolicyCartResponse.builder()
                .cartId(1)
                .totalPremium(1000.0)
                .policyId("123")
                .clientUsername("TestUser")
                .build();

        when(cartService.getCartItems(1)).thenReturn(expectedResponse);

        // Initialize MockMvc using standalone setup
        mockMvc = MockMvcBuilders.standaloneSetup(appController).build();

        // Perform the HTTP request and verify the response
        mockMvc.perform(get("/insuranceCart/getCartItems/{cartId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartId").value(1))
                .andExpect(jsonPath("$.totalPremium").value(1000.0))
                .andExpect(jsonPath("$.policyId").value("123"))
                .andExpect(jsonPath("$.clientUsername").value("TestUser"));

        // Additional test to cover getTotalPremium
        mockMvc.perform(get("/insuranceCart/getTotalPremium/{cartId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("1000.0"));
    
    }

 

    @Test
    public void testGetCartByClient() throws Exception {
        CartEntity cartEntity = CartEntity.builder()
                .cartId(1)
                .clientUsername("TestUser")
                .policyId("123")
                .totalPremium(1000.0)
                .build();
        when(cartService.findByClientUsername("TestUser")).thenReturn(cartEntity);

        mockMvc = MockMvcBuilders.standaloneSetup(appController).build();

        mockMvc.perform(get("/insuranceCart/getCartByUsername/{clientUsername}", "TestUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartId").value(1))
                .andExpect(jsonPath("$.clientUsername").value("TestUser"))
                .andExpect(jsonPath("$.policyId").value("123"))
                .andExpect(jsonPath("$.totalPremium").value(1000.0));
    }

  

//    @Test
//    public void testAddPolicyToCart() throws Exception {
//
//    	PolicyCartRequest policyRequest = PolicyCartRequest.builder()
//              .policyId("456")
//              .clientUsername("TestUser")
//              .premium(1200.0)
//              .build();
//      //  policyRequest.setPolicyId("123");
////        policyRequest.setClientUsername("TestUser");
//
//        when(cartService.addPolicyToCart(Collections.singletonList(policyRequest))).thenReturn("Policy added successfully");
//
//        mockMvc = MockMvcBuilders.standaloneSetup(appController).build();
//
//        mockMvc.perform(post("/insuranceCart/addPolicyToCart")
//                .contentType("application/json")
//                .content("[{\"policyId\":\"456\",\"clientUsername\":\"TestUser\",\"premium\":1200.0}]"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Policy added successfully"));
//    }

    @Test
    public void testUpdatePolicy() throws Exception {
        CartEntity updatedCartEntity = CartEntity.builder()
                .cartId(1)
                .clientUsername("TestUser")
                .policyId("123")
                .totalPremium(1200.0)
                .build();

        when(cartService.updatePolicyToCart(updatedCartEntity)).thenReturn("Updated");

        mockMvc = MockMvcBuilders.standaloneSetup(appController).build();

        mockMvc.perform(post("/insuranceCart/updatePolicy")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cartId\":1,\"clientUsername\":\"TestUser\",\"policyId\":\"123\",\"totalPremium\":1200.0}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Updated"));
    }



   
    
}
