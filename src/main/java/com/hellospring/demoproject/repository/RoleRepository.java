package com.hellospring.demoproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.hellospring.demoproject.enity.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
}

