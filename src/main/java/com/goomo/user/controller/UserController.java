package com.goomo.user.controller;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@GetMapping(value = "/users")
	public ResponseEntity<User[]> processFetchAllUsers(){
		Random rand = new Random();
		Integer rand_int1 = rand.nextInt(1000); 
		User user = User.builder()
						.account(new BigDecimal(11))
						.dept("Mechanical Engineering")
						.name("Sasikumar")
						.orderId(UUID.randomUUID().toString())
						.userId(rand_int1.longValue())
						.build();
		User[] users = new User[1];
		users [0] = user;
		return ResponseEntity.ok(users);
	}
	
	
}


