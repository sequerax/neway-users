package com.neway.user.service;

import com.neway.user.entitys.User;
import com.neway.user.repository.UserRepository;
import java.util.NoSuchElementException;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  @Autowired private final UserRepository userRepository;

  @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public User createUser(User user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  @Override
  public User getUserById(Integer id) {
    return userRepository.findById(id).orElse(new User());
  }

  @Override
  public void updateUser(User user) throws NotFoundException {
    try {
      User updateUser = userRepository.findById(user.getId()).get();
      if (updateUser == null) {
        throw new NotFoundException("User Not Found id: " + user.getId());
      }
      userRepository.save(user);
    } catch (NoSuchElementException ex) {
      throw new NotFoundException("User Not Found id: " + user.getId());
    }
  }

  @Override
  public User getUserByUserName(String userName) {
    return userRepository.findByUserName(userName);
  }
}
