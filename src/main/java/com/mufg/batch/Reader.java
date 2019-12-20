package com.mufg.batch;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.mufg.model.Users;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Reader implements ItemReader<Users> {

	@Value("${rest.api.url}")
	private String apiUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private int nextStudentIndex;
	private List<Users> usersData;
	
	@Override
	public Users read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		log.info("Reading the information of the next student");
        if (usersDataIsNotInitialized()) {
        	usersData = fetchUserDataFromAPI();
        }
        Users nextUser = null;
        if (nextStudentIndex < usersData.size()) {
        	nextUser = usersData.get(nextStudentIndex);
            nextStudentIndex++;
        }
        log.info("Found user: {}", nextUser);
        return nextUser;
	}

	private List<Users> fetchUserDataFromAPI() {
		log.debug("Fetching user data from an external API by using the url: {}", apiUrl);
        ResponseEntity<Users[]> response = restTemplate.getForEntity(apiUrl, Users[].class);
        Users[] userData = response.getBody();
        log.debug("Found {} users", userData.length);
        return Arrays.asList(userData);
	}

	private boolean usersDataIsNotInitialized() {
		return this.usersData == null;
	}
	
	
}
