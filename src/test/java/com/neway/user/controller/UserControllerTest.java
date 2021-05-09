package com.neway.user.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.neway.user.entitys.User;
import com.neway.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

  @InjectMocks private UserController userController;

  @Mock private UserService userService;

  @Test
  void shouldGetUserSuccess() {

    when(this.userService.getUserById(any())).thenReturn(buildUser());
    ResponseEntity<User> user = userController.getUserById(1);
    assertTrue(user.getBody().getEmail().equals("neway.user@neway.com"));
  }

  @Test
  void shouldCreateUserSuccessfully() {
    when(this.userService.createUser(any())).thenReturn(buildUser());
    User user = userController.createUser(buildUser());
    assertNotNull(user);
    assertTrue("neway-test".equals(user.getUserName()));
    assertTrue("neway.user@neway.com".equals(user.getEmail()));
  }

  private User buildUser() {
    User user = new User();
    user.setId(1);
    user.setUserName("neway-test");
    user.setEmail("neway.user@neway.com");
    return user;
  }
}
