package com.mufg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mufg.entity.CorporateUserEntity;

@Repository
public interface CorporateUserRepository extends JpaRepository<CorporateUserEntity, Long>{

}
