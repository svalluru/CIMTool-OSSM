// camel-k: language=java
import org.apache.camel.builder.RouteBuilder;
public class AzureInstances extends RouteBuilder {
  @Override
  public void configure() throws Exception {
				
		rest()
		.post("/startinstances")
		.produces("application/json")
		.to("direct:startins") ;

    from("direct:startins")
    .log("Starting Azure instances")
    .setBody(simple("{\"Order\":\"Placed Service A\"}"));
  }
}