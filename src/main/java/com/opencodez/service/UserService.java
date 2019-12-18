package com.opencodez.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.opencodez.entity.Users;
import com.opencodez.repository.UsersRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	private UsersRepository repository;
	
	public void processInsert(String text) {
		log.info("UserService :: processInsert() :: Started " + text);
		Users user = new Gson().fromJson(text, Users.class);
		repository.save(user);
		log.info("UserService :: processInsert() :: Ends " + text);
	}

}
