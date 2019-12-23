package com.mufg.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.mufg.entity.AuthorizedUserEntity;
import com.mufg.entity.CorporateUserEntity;
import com.mufg.entity.NonAuthorizedUserEntity;
import com.mufg.entity.UserEntity;
import com.mufg.model.AuthorizedUser;
import com.mufg.model.CorporateUser;
import com.mufg.model.NonAuthorizedUser;
import com.mufg.model.User;
import com.mufg.repository.AuthorizedUserRepository;
import com.mufg.repository.CorporateUserRepository;
import com.mufg.repository.NonAuthorizedUserRepository;
import com.mufg.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Writer implements ItemWriter<Object>{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AuthorizedUserRepository authUserRepo;
	
	@Autowired
	private NonAuthorizedUserRepository nonAuthUserRepo;
	
	@Autowired
	private CorporateUserRepository corpUserRepo;
	
	@Override
	@Transactional
	public void write(List<? extends Object> objects) throws Exception {
		log.info("Writer process started");
		Gson gson = new Gson();
		objects.forEach(i -> log.debug("Received the information of a details: {}", i));
		Object response = objects.get(0);
		if(response instanceof User) {
			userRepo.save(gson.fromJson(gson.toJson(objects.get(0)),UserEntity.class));
		}else if(response instanceof AuthorizedUser) {
			authUserRepo.save(gson.fromJson(gson.toJson(objects.get(0)),AuthorizedUserEntity.class));
		}else if(response instanceof NonAuthorizedUser) {
			nonAuthUserRepo.save(gson.fromJson(gson.toJson(objects.get(0)),NonAuthorizedUserEntity.class));
		}else if(response instanceof CorporateUser) {
			corpUserRepo.save(gson.fromJson(gson.toJson(objects.get(0)),CorporateUserEntity.class));
		}
		log.info("Writer process ends");
	}
	
}
