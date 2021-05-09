package com.neway.user.repository;

import com.neway.user.entitys.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

  User findByUserName(String userName);
}
