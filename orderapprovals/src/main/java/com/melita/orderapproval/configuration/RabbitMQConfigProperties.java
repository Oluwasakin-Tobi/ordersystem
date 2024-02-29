package com.melita.orderapproval.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "rabbitmq")
@Data
public class RabbitMQConfigProperties {
	
	private String orderTakingQueue;
	private String orderNotificationExchange;
	private String orderNotificationQueue;
	private String orderNotificationRoutingKey;
	private String host;
	private String username;
	private String password;

}
