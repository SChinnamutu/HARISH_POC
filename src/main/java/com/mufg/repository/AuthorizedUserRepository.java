package com.mufg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mufg.entity.AuthorizedUserEntity;


@Repository
public interface AuthorizedUserRepository extends JpaRepository<AuthorizedUserEntity, Long>{

}
