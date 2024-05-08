package com.hellospring.demoproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hellospring.demoproject.enity.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("SELECT u from User u where u.email = :email")
    public User getUserByEmail(@Param("email") String email);

    public Long countById(Integer id);

    @Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email, ' ', u.firstName, ' ', u.lastName, ' ') LIKE %?1%")
    public Page<User> findAll(String keyword, Pageable pageable);

    @Query("update User u SET u.enabled = ?2 where u.id = ?1")
    @Modifying
    public void updateEnabledStatus(Integer id, boolean enabled);

}
