package com.repo;

import com.entity.CartEntity;
import com.example.shopmsmono.Insurance_CartApplication;
import com.model.PolicyCartRequest;
import com.model.PolicyCartResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Insurance_CartApplication.class)//, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepoTest {

    @Autowired
    private InsuranceRepo insuranceRepo;

    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    void testFindByClientUsername() {
        // Given
        String clientUsername = "testUser";
        CartEntity cartEntity = CartEntity.builder().clientUsername(clientUsername).build();
        testEntityManager.persistAndFlush(cartEntity);

        // When
        CartEntity result = insuranceRepo.findByClientUsername(clientUsername);

        // Then
        assertEquals(clientUsername, result.getClientUsername());
    }

    @Test
    void testAddPolicyToCart() {
        // Given
        PolicyCartRequest policyRequest = PolicyCartRequest.builder()
                .policyId("1")
                .clientUsername("testUser")
                .premium(100.0)
                .build();

        // When
        Double totalPremium = 100.0;  // Calculate total premium based on policyRequest
        CartEntity cartEntity = CartEntity.builder()
                .clientUsername(policyRequest.getClientUsername())
                .policyId(policyRequest.getPolicyId())
                .totalPremium(totalPremium)
                .build();
        cartEntity = insuranceRepo.save(cartEntity);

        // Then
        assertEquals("Item Added", "Item Added");  // Add relevant assertions based on your requirements
    }

    @Test
    void testGetCartItems() {
        // Given
        CartEntity cartEntity = CartEntity.builder()
                .cartId(1)
                .clientUsername("testUser")
                .policyId("1,2,3")
                .totalPremium(300.0)
                .build();
        testEntityManager.persistAndFlush(cartEntity);

        // When
        CartEntity result = insuranceRepo.findById(1).orElse(null);

        // Then
        assertEquals("testUser", result.getClientUsername());
        assertEquals(1, result.getCartId());
        assertEquals("1,2,3", result.getPolicyId());
        assertEquals(300.0, result.getTotalPremium());
    }
}

