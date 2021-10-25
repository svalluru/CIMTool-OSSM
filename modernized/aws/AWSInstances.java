// camel-k: language=java dependency=camel:aws2-ec2
// camel-k: dependency=mvn:org.apache.camel:camel-ognl
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
		.to("direct:startins") ;

		from("direct:startins")
		.log("key calling")
		//.log("${body.instanceid}")
		.process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {
				System.out.println("proc");
			}
		})
		//.setHeader(AWS2EC2Constants.INSTANCES_IDS,method(this, "getMasterIds()"))
		//.to("aws-ec2:startEC2?accessKey=xxxx&secretKey=xxxx&operation=startInstances")
		;
	}
}

