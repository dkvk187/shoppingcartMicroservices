package com.shoppingcart.microservices.producer;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.shoppingcart.microservices.message.IncomingMessage;

@RestController
@RequestMapping("/rest/mapping")
public class ProducerResource implements ErrorController {

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private Queue queue;
	
	private static final String PATH = "/error";

	@PostMapping(path = "/shoppingDetails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public IncomingMessage publishShoppingCard(@RequestBody IncomingMessage incomingMessage) {
		Gson gson = new Gson();
		jmsTemplate.convertAndSend(queue, gson.toJson(incomingMessage));
		return incomingMessage;
	}

	@RequestMapping(value = PATH)
	public String error() {
		return "Error handling";
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}
}
