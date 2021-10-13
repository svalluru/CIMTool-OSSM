package azure;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class AzureResourceClient extends RouteBuilder {


	@Override
	public void configure() {

		from("direct:startResources")
        .setBody(simple("Azure instances stopped !!!"))
		;

	}

	
	
	
}
