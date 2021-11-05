// camel-k: language=java dependency=camel:aws2-ec2
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.aws2.ec2.AWS2EC2Constants;
public class AWSInstances extends RouteBuilder {
	@Override
	public void configure() throws Exception {

		rest()
		.post("/startinstances")
		.produces("application/json")
		.consumes("application/json")
		.to("direct:checkcreds");
		

		from("direct:checkcreds")
		.removeHeaders("*")
		.to("http://db-controller.controllers:80/checkcreds?token=sritoken1")
		//.log("${body}")
		.streamCaching()
		.choice()
		.when(bodyAs(String.class).contains("accesskey"))
			.to("direct:startins")
		.otherwise().log("access NOT allowed")	
		 ;

		from("direct:startins")
		.unmarshal().json()
		
		//.log(simple("${body}")
		.log("key calling")

		//.log("${body[secretkey]}")	
		.setHeader(AWS2EC2Constants.INSTANCES_IDS, simple("${body[instanceids]}"))
		.to("aws2-ec2:startEC2?region=ca-central-1&accessKey=${body[accesskey]}&secretKey=${body[secretkey]}&operation=startInstances")
		;
	}
}

