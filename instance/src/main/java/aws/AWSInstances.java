package aws;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class AWSInstances extends RouteBuilder {
	@Override
	public void configure() throws Exception {

		rest()
		.post("/startinstances")
		.produces("application/json")
		.consumes("application/json").outType(InstanceDetails.class)
		.to("direct:startins") ;

		from("direct:startins")
		.process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				InstanceDetails body = exchange.getIn().getBody(InstanceDetails.class);
				exchange.setProperty("accesskey", "mykey");
				System.out.println(body.getInstanceid());
				
			}
		})
		//.setHeader(AWS2EC2Constants.INSTANCES_IDS,method(this, "getMasterIds()"))
		.log("${exchangeProperty.accesskey}")
		.to("aws-ec2:startEC2?accessKey=xxxx&secretKey=xxxx&operation=startInstances")
		.log("${body}")
		;
	}

}

class InstanceDetails {

	private String instanceid;
	private String accessKey;
	private String secretKey;

	public String getInstanceid() {
		return instanceid;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public void setInstanceid(String instanceid) {
		this.instanceid = instanceid;
	}
}
