package com.melita.ordernotification.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.melita.orderapproval.dto.OrderDTO;
import com.melita.ordernotification.service.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationListener {

	private final EmailService emailService;

	private static final String ORDER_SUBJECT = "Order Processing";

	@Value("${mail.agentEmail}")
	private String agentEmail;

	@RabbitListener(queues = "${rabbitmq.orderNotificationQueue}")
	public void orderListener(OrderDTO order) {
		log.info("test notif" + agentEmail + order);
		emailService.sendMail(agentEmail, ORDER_SUBJECT, emailBody(order));

	}

	private String emailBody(OrderDTO order) {
		return String.format(
				"Dear Agent, \n A newly accepted order with id [%s] requires your approval.\n"
						+ "The installation address is %s. The customer who made the order is %s %s "
						+ "with email %s and mobile number %s",
				order.getId(), order.getInstallationAddress(), order.getCustomerDetails().getFirstName(),
				order.getCustomerDetails().getLastName(), order.getCustomerDetails().getEmail(),
				order.getCustomerDetails().getPhoneNo());
	}

}
