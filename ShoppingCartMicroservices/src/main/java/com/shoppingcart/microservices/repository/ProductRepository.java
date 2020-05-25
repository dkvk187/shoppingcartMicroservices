package com.shoppingcart.microservices.repository;

import org.springframework.data.repository.CrudRepository;

import com.shoppingcart.microservices.model.ProductItems;


public interface ProductRepository extends CrudRepository<ProductItems, Integer> {

	

}
