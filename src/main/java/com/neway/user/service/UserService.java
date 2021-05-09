package com.neway.user.service;

import com.neway.user.entitys.User;
import javassist.NotFoundException;

public interface UserService {

  User createUser(User user);

  User getUserById(Integer id);

  void updateUser(User user) throws NotFoundException;

  User getUserByUserName(String userName);
}
