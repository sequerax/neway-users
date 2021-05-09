package com.neway.user.controller;

import com.neway.user.entitys.User;
import com.neway.user.service.UserService;
import javassist.NotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

  @Autowired private final UserService userService;

  @PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
  public User createUser(@Valid @RequestBody User user) {
    return userService.createUser(user);
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> getUserById(@PathVariable("id") @NotEmpty Integer id) {
    return ResponseEntity.ok(userService.getUserById(id));
  }

  @GetMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> login(@Valid @RequestBody User user) {
    return ResponseEntity.ok(user);
  }

  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> updateUser(@Valid @RequestBody User user) throws NotFoundException {
    try {
      userService.updateUser(user);
      return ResponseEntity.ok("User updated successfully");
    } catch (NotFoundException ex) {
      return ResponseEntity.notFound().build();
    }
  }
}
