package com.melita.ordertaking.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "rabbitmq")
@Data
public class RabbitMQConfigProperties {
	
	private String orderTakingExchange;
	private String orderTakingQueue;
	private String orderTakingRoutingKey;
	private String host;
	private String username;
	private String password;

}
