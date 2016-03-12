package com.msys.web.view;

import com.msys.repository.OrderRepository;
import com.msys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.msys.entity.Article;
import com.msys.entity.Order;
import com.msys.entity.OrderItem;
import com.msys.entity.Supplier;
import com.msys.entity.User;

@EnableJpaRepositories("com.msys.repository")
@EnableAutoConfiguration
@EntityScan("com.msys.entity")
@ComponentScan("com.msys.web.login")
@SpringBootApplication

public class StoreMSysApplication /*implements CommandLineRunner*/ {

	private static final Logger log = LoggerFactory.getLogger(StoreMSysApplication.class);

	@Autowired
	private OrderRepository orderRepository;

	public static void main(String[] args) {
		SpringApplication.run(StoreMSysApplication.class, args);
	}

	/*@Override
	@Transactional
	public void run(String... strings) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.mm.yyyy");

		Order order1 = new Order();
		order1.setDeliveryDate(formatter.parse("14.12.2016"));
		order1.setValidFrom(formatter.parse("13.09.2016"));
		order1.setValidTo(formatter.parse("11.07.2015"));
		order1.setAvalabileAt(2);

		OrderItem orderItem1 = new OrderItem();
		orderItem1.setArticles(new Article(1));
		orderItem1.setQuantity(3);
		orderItem1.setSuppliers(new Supplier(3));
		orderItem1.setOrders(order1);

		OrderItem orderItem2 = new OrderItem();
		orderItem2.setArticles(new Article(3));
		orderItem2.setQuantity(6);
		orderItem2.setSuppliers(new Supplier(5));
		orderItem2.setOrders(order1);

		Set<OrderItem> setOrderItem = new HashSet();
		setOrderItem.add(orderItem1);
		setOrderItem.add(orderItem2);

		order1.setOrderItems(setOrderItem);
		orderRepository.save(order1);
	}*/

	@Bean
	public CommandLineRunner loadData(UserRepository userRepo) {
		return (args) -> {

			log.info("User found with findAll():");
			log.info("-------------------------------");
			for (User userAll : userRepo.findAll()) {
				log.info(userAll.toString());
			}
			log.info("");

			// fetch an individual USer by ID
			User userOne = userRepo.findOne(1L);
			log.info("User found with findOne(7L):");
			log.info("--------------------------------");
			log.info(userOne.toString()); // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			log.info("");

			// fetch Users by last name
			log.info("USer found with findByName('adrian.turtoi@gmail.com'):");
			log.info("--------------------------------------------");
			log.info(userRepo.findByEmail("adrianturtoi@gmail.com").toString());
			log.info("");

		};
	}
}
