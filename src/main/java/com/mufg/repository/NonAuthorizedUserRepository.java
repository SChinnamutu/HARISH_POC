package com.mufg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mufg.entity.NonAuthorizedUserEntity;

@Repository
public interface NonAuthorizedUserRepository extends JpaRepository<NonAuthorizedUserEntity, Long>{

}
