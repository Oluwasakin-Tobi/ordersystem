package com.melita.ordertaking.validator;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.melita.ordertaking.configuration.ProductPackageConfigProperties;
import com.melita.ordertaking.dto.CustomerDTO;
import com.melita.ordertaking.dto.OrderDTO;
import com.melita.ordertaking.dto.ProductDTO;
import com.melita.ordertaking.exception.OrderBadRequestException;
import com.melita.ordertaking.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderValidator {

	private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
	private static final String PHONE_REGEX = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$";

	final ProductPackageConfigProperties packageConfig;

	public void validate(OrderDTO order) {
		if (Objects.isNull(order.getProducts()) || order.getProducts().isEmpty()) {
			throw new OrderBadRequestException("Product cannot be empty");
		}

		if (Objects.isNull(order.getProducts()) || order.getProducts().isEmpty()) {
			throw new OrderBadRequestException("Customer details cannot be empty");
		}

		if (Objects.isNull(order.getInstallationAddress()) || Objects.isNull(order.getInstallationDateTime())) {
			throw new OrderBadRequestException("Installation detail fields cannot be null");
		}

		validateCustomerDetails(order.getCustomerDetails());
		validateProductDetails(order.getProducts());

	}

	private void validateCustomerDetails(CustomerDTO customer) {

		if (Objects.isNull(customer.getEmail()) || Objects.isNull(customer.getPhoneNo())
				|| Objects.isNull(customer.getFirstName()) || Objects.isNull(customer.getLastName())) {
			throw new OrderBadRequestException("Required customer detail fields cannot be null");
		}

		boolean emailValid = Pattern.matches(EMAIL_REGEX, customer.getEmail());

		if (!emailValid) {
			throw new OrderBadRequestException("Email not in right format");
		}

		boolean phoneValid = Pattern.matches(PHONE_REGEX, customer.getPhoneNo());

		if (!phoneValid) {
			throw new OrderBadRequestException("Phone number not valid");
		}

	}

	private void validateProductDetails(List<ProductDTO> products) {

		products.stream().forEach(product -> validateProduct(product));
	}

	private void validateProduct(ProductDTO product) {

		switch (product.getType().toUpperCase()) {
		case "INTERNET":
			validatePackage(product, packageConfig.getInternetPackages());
			break;
		case "TV":
			validatePackage(product, packageConfig.getTvPackages());
			break;
		case "MOBILE":
			validatePackage(product, packageConfig.getMobilePackages());
			break;
		case "TELEPHONY":
			validatePackage(product, packageConfig.getTelephonyPackages());
			break;
		default:
			throw new ResourceNotFoundException(String.format("Product type [%s] not found", product.getType()));
		}

	}

	private void validatePackage(ProductDTO product, List<String> packages) {

		boolean isValid = packages.stream().anyMatch(p -> p.equalsIgnoreCase(product.getPackages()));

		if (!isValid) {
			throw new ResourceNotFoundException(
					String.format("Product [%s] does not have [%s] package", product.getType(), product.getPackages()));
		}
	}

}
