package com.melita.ordertaking.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "product-packages")
@Data
public class ProductPackageConfigProperties {
	
	private List<String> tvPackages;
	private List<String> mobilePackages;
	private List<String> internetPackages;
	private List<String> telephonyPackages;

}
