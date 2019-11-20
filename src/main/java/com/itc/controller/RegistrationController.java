package com.itc.controller;

import com.itc.domain.Role;
import com.itc.domain.User;
import com.itc.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "false")
public class RegistrationController {
    private final UserDao userDao;

    @Autowired
    public RegistrationController(UserDao userDao) {
        this.userDao = userDao;
    }

    @PostMapping("/login")
    public ResponseEntity enter(@RequestParam String email,
                                @RequestParam String password) {
        User userFromDb = userDao.findByEmail(email);

        if (userFromDb == null || !userFromDb.getPassword().equals(password)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        } else {
            userFromDb.setAuthentificated(true);
            userDao.save(userFromDb);
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

    @PostMapping(value = "/registration",
            consumes = "application/json",
            produces = "application/json")
    public User addUser(@RequestBody User user) {
        User userFromDb = userDao.findByEmail(user.getEmail());

        if (userFromDb != null) {
            return userFromDb;
            // todo error
        } else {
            User newUser = new User();
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            newUser.setActive(true);
            newUser.setRoles(Collections.singleton(Role.USER));
            userDao.save(newUser);
            return user;
        }
    }
}
