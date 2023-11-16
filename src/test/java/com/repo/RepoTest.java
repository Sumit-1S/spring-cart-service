package com.repo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.entity.CartEntity;
import com.service.CartServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RepoTest {

    @InjectMocks
    private CartServiceImpl cartService;

    @Mock
    private InsuranceRepo insuranceRepo;

    @Test
    public void testFindByClientUsername() {
        String clientUsername = "User123";

        // Create a sample response from the repository
        CartEntity expectedCartEntity = CartEntity.builder()
                .cartId(1)
                .clientUsername(clientUsername)
                .policyId("123")
                .totalPremium(1000.0)
                .build();

        when(insuranceRepo.findByClientUsername(clientUsername)).thenReturn(expectedCartEntity);

        // Call the method to be tested
        CartEntity result = cartService.findByClientUsername(clientUsername);

        // Assertions or verifications based on the expected behavior
        assertEquals(expectedCartEntity, result);

        // Verify that the repository method was called with the expected argument
        verify(insuranceRepo).findByClientUsername(clientUsername);
    }

    // Add more tests for other repository methods as needed
}
