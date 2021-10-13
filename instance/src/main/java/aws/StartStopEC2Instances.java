/*
 * Copyright (C) Red Hat, Inc.
 * http://www.redhat.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package aws;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.aws.ec2.EC2Constants;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StartStopEC2Instances extends RouteBuilder {

	@Value("${aws.ec2.startTime}")
	private String startTime;
	@Value("${aws.ec2.stopTime}")
	private String stopTime;
	@Value("${aws.ec2.delay.ms}")
	private int delay;

	@Override
	public void configure() {

		restConfiguration().component("undertow")
		.bindingMode(RestBindingMode.auto)
		.port(9377)
	    .contextPath("/im");

		
		rest()
		.get("/startec2")
		.produces("application/json")
		.to("direct:startEC2");

		from("direct:startEC2")
		//from("timer:timerName?period=1s")
		.setHeader(EC2Constants.INSTANCES_IDS,method(EC2Client.class, "getMasterIds()"))
		.to("aws-ec2:startEC2?operation=startInstances&amazonEc2Client=#ec2Client")
		.log("${body}")
		.delay(delay)
		.setHeader(EC2Constants.INSTANCES_IDS,method(EC2Client.class, "getWorkerIds()"))
		.to("aws-ec2:startEC2?operation=startInstances&amazonEc2Client=#ec2Client")
		.log("${body}")
		;


		from("direct:startEC")
		//from("timer:timerName?period=1s")
		.setHeader(EC2Constants.INSTANCES_IDS,method(EC2Client.class, "getWorkerIds()"))
		.to("aws-ec2:stopEC2?operation=stopInstances&amazonEc2Client=#ec2Client")
		.log("${body}")
		.delay(delay)
		.setHeader(EC2Constants.INSTANCES_IDS,method(EC2Client.class, "getMasterIds()"))
		.to("aws-ec2:stopEC2?operation=stopInstances&amazonEc2Client=#ec2Client")
		.log("${body}");



	}

}
