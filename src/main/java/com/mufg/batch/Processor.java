package com.mufg.batch;

import java.util.UUID;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.mufg.model.Users;

@Component
public class Processor implements ItemProcessor<Users, Users> {

	@Override
	public Users process(Users user) throws Exception {
		user.setOrderId(UUID.randomUUID().toString());
		return user;
	}

}
