package com.mufg.registration.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mufg.registration.entity.RegistrationEntity;

@Repository
public interface RegistrationRepository extends CrudRepository<RegistrationEntity, Long>  {

}


