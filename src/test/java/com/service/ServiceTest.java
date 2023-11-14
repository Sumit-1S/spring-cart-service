package com.service;

import com.entity.CartEntity;
import com.model.PolicyCartRequest;
import com.model.PolicyCartResponse;
import com.repo.InsuranceRepo;
import com.service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @InjectMocks
    private CartServiceImpl cartService;
    


    @Mock
    private InsuranceRepo insuranceRepo;
    
//    @Before
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//    }

    public void init() {
		MockitoAnnotations.openMocks(this);
	}
    @Test
    public void testAddPolicyToCart() {
        // Create a sample requestPolicy list
        List<PolicyCartRequest> requestPolicyList = new ArrayList<>();
        //requestPolicyList.add(new PolicyCartRequest("123","User123",1000.0));
        PolicyCartRequest request = PolicyCartRequest.builder()
        	    .policyId("123")
        	    .clientUsername("User123")
        	    .premium(1000.0)
        	    .build();

        	requestPolicyList.add(request);

        // Create a sample CartEntity object for testing
        CartEntity cartEntity = CartEntity.builder()
                .clientUsername("TestUser")
                .policyId("123")
                .totalPremium(1000.0)
                .build();

        // Mock the behavior of insuranceRepo.save
        Mockito.when(insuranceRepo.save(Mockito.any(CartEntity.class))).thenReturn(cartEntity);

        // Call the method to be tested
        String result = cartService.addPolicyToCart(requestPolicyList);

        // Assertions or verifications based on the expected behavior
        // Verify that save method was called with the expected CartEntity
        Mockito.verify(insuranceRepo).save(Mockito.any(CartEntity.class));

        // Assert the result or perform other checks as needed
        assertEquals(" Item Added", result);
    }
    

    @Test
    public void testGetCartItems() {
        // Create a sample CartEntity object and an expected PolicyCartResponse
        CartEntity cartEntity = CartEntity.builder()
                .cartId(1)
                .clientUsername("TestUser")
                .policyId("123")
                .totalPremium(1000.0)
                .build();

        // Mock the behavior of insuranceRepo.findById
        Mockito.when(insuranceRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(cartEntity));

        // Call the method to be tested
        PolicyCartResponse result = cartService.getCartItems(1); // Pass a valid cartId

        // Assertions or verifications based on the expected behavior
        // Verify that findById method was called with the expected cartId
        Mockito.verify(insuranceRepo).findById(1); // Use the appropriate cartId

        // Assert the result or perform other checks as needed
        PolicyCartResponse expectedResponse = PolicyCartResponse.builder()
                .cartId(cartEntity.getCartId())
                .totalPremium(cartEntity.getTotalPremium())
                .policyId(cartEntity.getPolicyId())
                .clientUsername(cartEntity.getClientUsername())
                .build();

        assertEquals(expectedResponse, result);
    }
}
