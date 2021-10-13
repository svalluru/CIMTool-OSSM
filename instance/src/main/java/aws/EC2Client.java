package aws;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2Client;

@Configuration
public class EC2Client {

	@Value("${aws.accesskey}")
	private String accessKey;
	@Value("${aws.secretkey}")
	private String secretKey;
	@Value("${aws.region}")
	private String region;
	@Value("${aws.master.ec2.instances}")
	private String masterinstances;
	@Value("${aws.worker.ec2.instances}")
	private String workerinstances;
	
	@Bean(name = "ec2Client")
	  public AmazonEC2Client getEc2Client()  {

		AmazonEC2Client amazonEC2Client = new AmazonEC2Client(new BasicAWSCredentials(accessKey, secretKey));                                                                                                                                             
		amazonEC2Client.setEndpoint("ec2."+region+".amazonaws.com");
	    return amazonEC2Client;
	  }
	
	public List<String> getMasterIds(){
		
		String[] split = masterinstances.split(",");
		
		List<String> instances = Arrays.asList(split);
	
		return instances;
	}

	public List<String> getWorkerIds(){
		
		String[] split = workerinstances.split(",");
		
		List<String> instances = Arrays.asList(split);
	
		return instances;
	}
}
