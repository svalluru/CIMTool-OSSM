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
		.port(8080)
		.contextPath("/im");


		rest()
		.post("/startinstances")
		.produces("application/json")
		.consumes("application/json")
		.to("direct:startinstances");


		from("direct:startinstances")
		.choice()
			.when(header("provider").isEqualTo("aws")) .to("direct:startEC2")
			.when(header("provider").isEqualTo("azure")) .to("direct:startResources")
		.end();



	}

}
