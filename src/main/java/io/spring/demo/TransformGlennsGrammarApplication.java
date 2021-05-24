package io.spring.demo;

import java.util.function.Function;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TransformGlennsGrammarApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransformGlennsGrammarApplication.class, args);
	}

	@Bean
	public Function<String, String> transform() {
		return (payload) -> {
			Message message = parseMessage(payload);
			if(message.getData().toUpperCase().startsWith("ARE WE")) {
				message.setData("We are" + message.getData().substring(6));
			}
			if(message.getData().toUpperCase().startsWith("IS GLENN")  && !message.getData().contains("typical")) {
				message.setData("Glenn is" + message.getData().substring(8) );
			}
			return message != null ? serializeMessage(message) : null;
		};
	}

	private Message parseMessage(String payload) {
		ObjectMapper objectMapper = new ObjectMapper();
		Message result = null;
		try {
			result = objectMapper.readValue(payload, Message.class);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	private String serializeMessage(Message message) {
		ObjectMapper objectMapper = new ObjectMapper();
		String result = null;
		try {
			result = objectMapper.writeValueAsString(message);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

}
