package com.cim.instance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="aws,azure,com.cim.instance")
public class InstanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstanceApplication.class, args);
	}

}
