package com.shoppingcart.microservices;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.shoppingcart.microservices.message.IncomingMessage;
import com.shoppingcart.microservices.producer.ProducerResource;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ShoppingCartMicroservicesApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ProducerResource producerResource;

	IncomingMessage mockIncomingMessage = new IncomingMessage(5, "USB 4GB Samsung", "Electronics", 45.00);

	String exampleIncomingMessageJson = "{\"id\":5," + "\"name\":\"USB 4GB Samsung\"," + "\"category\":\"Electronics\","
			+ "\"price\":45.0" + "}";

	@Test
	public void createIncomingMessage() throws Exception {

		Mockito.when(producerResource.publishShoppingCard(Mockito.any(IncomingMessage.class)))
				.thenReturn(mockIncomingMessage);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rest/mapping/shoppingDetails")
				.accept(MediaType.APPLICATION_JSON).content(exampleIncomingMessageJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

		assertEquals(exampleIncomingMessageJson, response.getContentAsString());

	}
}
