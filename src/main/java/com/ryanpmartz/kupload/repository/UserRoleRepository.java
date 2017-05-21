package com.ryanpmartz.kupload.repository;

import com.ryanpmartz.kupload.domain.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

}

