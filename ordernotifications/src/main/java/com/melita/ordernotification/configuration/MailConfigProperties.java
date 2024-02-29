package com.melita.ordernotification.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "mail")
@Data
public class MailConfigProperties {
	
	private String host;
	private String port;
	private String ssl;
	private String auth;
	private String username;
	private String password;
	private String from;

}
