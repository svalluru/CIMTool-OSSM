package com.cim.instance;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class InstanceController {

	@PostMapping(value="/startinstances", consumes = "application/json", produces = "application/json")
	public String startInstances(@RequestBody InstanceDetails instDetails, HttpServletResponse response,@RequestHeader String provider) {
		
		if("aws".equalsIgnoreCase(provider)) {
			WebClient webClient2 = WebClient.create("http://0.0.0.0:9377/camel");
			webClient2.get()
			.uri("/startec2")
			.retrieve()
	        .bodyToFlux(String.class);
			
			
		} else if ("azure".equalsIgnoreCase(provider)) {
			
		} else {
			return "Provider not supported !!!";
		}
		return instDetails.getInstanceid()+"DONE"+provider;
	}

	@PostMapping("/stopinstances")
	public String stopInstances() {
		return "Greetings from Spring Boot!";
	}

	@PostMapping(value="/registerinstances", consumes = "application/json", produces = "application/json")
	public String registerInstances(@RequestBody InstanceDetails instDetails, HttpServletResponse response,@RequestHeader String provider) {
		
		if("aws".equalsIgnoreCase(provider)) {
			
		} else if ("azure".equalsIgnoreCase(provider)) {
			
		} else {
			return "Provider not supported !!!";
		}
		return instDetails.getInstanceid()+"DONE"+provider;
	}

	
}