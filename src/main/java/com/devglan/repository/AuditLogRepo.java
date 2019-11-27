package com.devglan.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.devglan.model.AuditLog;


@Repository
public interface AuditLogRepo  extends CrudRepository<AuditLog, Long>{

}
