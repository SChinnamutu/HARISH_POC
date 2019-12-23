package com.mufg.batch;

import java.util.ArrayList;
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

import com.mufg.model.AuthorizedUser;
import com.mufg.model.CorporateUser;
import com.mufg.model.NonAuthorizedUser;
import com.mufg.model.User;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Reader implements ItemReader<Object> {

	@Value("${rest.user.api.url}")
	private String userApiUrl;
	
	@Value("${rest.auth.user.api.url}")
	private String authUserApiUrl;
	
	@Value("${rest.non.auth.user.api.url}")
	private String nonAuthuserApiUrl;
	
	@Value("${rest.corp.user.api.url}")
	private String corpUserApiUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private int nextStudentIndex;
	
	private List<Object> usersData;
	
	@Override
	public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		log.info("Reading the information of the next student");
        Object nextUser = null;
        if(nextStudentIndex == 0) {
        	usersData = fetchUserDataFromAPI();
        }
        if (nextStudentIndex < usersData.size()) {
        	nextUser = usersData.get(nextStudentIndex);
            nextStudentIndex++;
        }else if(nextStudentIndex == usersData.size()) {
        	nextStudentIndex = 0;
        }
        log.info("Found user: {}", nextUser);
        return nextUser;
	}

	private List<Object> fetchUserDataFromAPI() {
		List<Object> list = new ArrayList<Object>();
		
		log.debug("Fetching user data from an external API by using the url: {}",userApiUrl);
        ResponseEntity<User[]> response = restTemplate.getForEntity(userApiUrl,User[].class);
        User[] userData = response.getBody();
        log.debug("Found {} users", userData.length);
        list.addAll(Arrays.asList(userData));
      
        log.debug("Fetching auth user data from an external API by using the url: {}",authUserApiUrl);
        ResponseEntity<AuthorizedUser[]> authReponse = restTemplate.getForEntity(authUserApiUrl,AuthorizedUser[].class);
        AuthorizedUser[] authUserData = authReponse.getBody();
        log.debug("Found {} auth users", authUserData.length);
        list.addAll(Arrays.asList(authUserData));
      
        
        log.debug("Fetching not auth user data from an external API by using the url: {}",nonAuthuserApiUrl);
        ResponseEntity<NonAuthorizedUser[]> nonAuthResponse = restTemplate.getForEntity(nonAuthuserApiUrl,NonAuthorizedUser[].class);
        NonAuthorizedUser[] nonAuthUserData = nonAuthResponse.getBody();
        log.debug("Found {} non auth users", nonAuthUserData.length);
        list.addAll(Arrays.asList(nonAuthUserData));
      
        
        log.debug("Fetching corporate user data from an external API by using the url: {}",corpUserApiUrl);
        ResponseEntity<CorporateUser[]> corpResponse = restTemplate.getForEntity(corpUserApiUrl,CorporateUser[].class);
        CorporateUser[] corpUserData = corpResponse.getBody();
        log.debug("Found {} corp users", corpUserData.length);
        list.addAll(Arrays.asList(corpUserData));
      
       
        return list;
	}

	
	
}
