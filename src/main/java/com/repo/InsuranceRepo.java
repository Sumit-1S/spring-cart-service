package com.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.entity.CartEntity;

@Repository
public interface InsuranceRepo extends CrudRepository<CartEntity, Integer>{
	CartEntity findByClientUsername(String clientUsername);

	String deleteByClientUsername(String clientUsername);
}