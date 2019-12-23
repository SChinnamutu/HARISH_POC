package com.mufg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mufg.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

}
