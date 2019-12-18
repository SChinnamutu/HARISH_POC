package com.mufg.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mufg.model.Users;
import com.mufg.queue.QueueSender;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Writer implements ItemWriter<Users>{

	@Autowired
	private QueueSender sender;
	
	@Override
	@Transactional
	public void write(List<? extends Users> users) throws Exception {
		log.info("Writer process started");
		sender.sendMessage("pdd2.queue", users.get(0));
		log.info("Writer process ends");
	}
	
}
