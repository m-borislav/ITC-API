package com.itc.controller;

import com.itc.domain.Role;
import com.itc.domain.User;
import com.itc.repos.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
public class RegistrationController  {
    @Autowired
    private UserDao userDao;

    @PostMapping("/login")
    public ResponseEntity enter(@RequestParam String email,
                                  @RequestParam String password) {
        User userFromDb = userDao.findByEmail(email);

        if (userFromDb == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        } else {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setActive(true);
            user.setAuthentificated(true);
            user.setRoles(Collections.singleton(Role.USER));
            userDao.save(user);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity exit(@RequestParam String email) {
        User userFromDb = userDao.findByEmail(email);
        if (userFromDb == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        } else {
            userFromDb.setAuthentificated(false);
            userDao.save(userFromDb);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<User> addUser(@RequestParam String email,
                                  @RequestParam String password) {
        User userFromDb = userDao.findByEmail(email);

        if (userFromDb != null) {
            return new ResponseEntity<>(userFromDb, HttpStatus.CONFLICT);
        } else {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            userDao.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }
}
