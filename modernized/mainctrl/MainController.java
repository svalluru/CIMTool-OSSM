// camel-k: language=java
import org.apache.camel.builder.RouteBuilder;
public class MainController extends RouteBuilder {
  @Override
  public void configure() throws Exception {
				
		rest()
		.post("/startinstances")
		.produces("application/json")
		.to("direct:startins") ;

    from("direct:startins")
    .log("Starting instances")
    .choice()
      .when(header("provider").isEqualTo("aws")) .to("http://aws-instances?bridgeEndpoint=true")
      .when(header("provider").isEqualTo("azure")) .to("direct:startResources")
    .end()
    .setBody(simple("{\"Order\":\"Placed Service A\"}"));

  }
}
