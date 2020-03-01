package org.example.controlers;

import org.example.domain.entityes.User;
import org.example.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping(value = "/api")
public class UserRestController {
    @Autowired
    private UserRepo userRepo;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> users() {
        return userRepo.findAll();
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> userById(@PathVariable Long id) {
        User user = userRepo.findById(id).orElse(null);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        User user = userRepo.findById(id).orElse(null);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = auth.getName();

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else if (user.getUsername().equalsIgnoreCase(loggedUsername)) {
            throw new RuntimeException("You cannot delete your account");
        } else {
            userRepo.delete(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exist");
        }
        return new ResponseEntity<>(userRepo.save(user), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public User updateUser(@RequestBody User user) {

        return userRepo.save(user);
    }
}