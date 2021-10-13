package com.cim.instance;


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class MainInstanceController extends RouteBuilder {


	@Override
	public void configure() {

		restConfiguration().component("undertow")
		.bindingMode(RestBindingMode.auto)
		.port(9377)
		.contextPath("/im");


		rest()
		.post("/startinstances")
		.produces("application/json")
		.consumes("application/json")
		.to("direct:startEC2");


		from("direct:startinstances")
		.choice()
			.when(header("provider").isEqualTo("aws")) .to("undertow:http://0.0.0.0:9377/camel/startec2")
		.end()
		.log("aws stopped");
		
		

	}

}
