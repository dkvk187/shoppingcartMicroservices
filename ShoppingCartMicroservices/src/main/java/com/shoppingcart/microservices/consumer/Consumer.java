package com.shoppingcart.microservices.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.shoppingcart.microservices.message.IncomingMessage;
import com.shoppingcart.microservices.model.ProductItems;
import com.shoppingcart.microservices.repository.ProductRepository;

@Component
@ComponentScan("com.shoppingcart")
@EnableJpaRepositories("com.shoppingcart.microservices.repository")
@EntityScan("com.shoppingcart.microservices.model")
public class Consumer {

	@Autowired
	private ProductRepository productRepository;

	@JmsListener(destination = "new.queue")
	public void listener(String message) {
		System.out.println("Received message: " + message);

		Gson gson = new Gson();

		IncomingMessage incomingMessage = gson.fromJson(message, IncomingMessage.class);

		System.out.println("==id===" + incomingMessage.getId());

		ProductItems productItems = productRepository.findById((int) incomingMessage.getId()).orElse(null);

		System.out.println("==shoppingCart===" + productItems);

		if (null == productItems) {
			productItems = new ProductItems();
			productItems.setId((int) incomingMessage.getId());
			productItems.setName(incomingMessage.getName());
			productItems.setCategory(incomingMessage.getCategory());
			productItems.setPrice(incomingMessage.getPrice());

			ProductItems productItems11 = productRepository.save(productItems);
			System.out.println("==productItems11===" + productItems11);
			System.out.println("==productItems11===" + productItems11.getId());
			System.out.println("==productItems11===" + productItems11.getCategory());
			System.out.println("==productItems11===" + productItems11.getName());
			System.out.println("==productItems11===" + productItems11.getPrice());
		}

	}

}
