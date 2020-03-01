package org.example.controlers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.domain.entityes.User;
import org.example.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class HomeRestController {
    @Autowired
    private UserRepo userRepo;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exist");
        }

        List<String> roles = new ArrayList<>();
        roles.add("USER");
        user.setRoles(roles);

        return new ResponseEntity<>(userRepo.save(user), HttpStatus.CREATED);
    }

    @RequestMapping("/user")
    public User user() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = auth.getName();
        return userRepo.findByUsername(loggedUsername);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> login(@RequestParam String username, @RequestParam String password){
        String token;
        User user = userRepo.findByUsername(username);
        Map<String, Object> tokenMap = new HashMap<>();
        if (user != null && user.getPassword().equals(password)) {
            token = Jwts.builder().setSubject(username).claim("roles", user.getRoles()).setIssuedAt(new Date())
                    .signWith(SignatureAlgorithm.HS256, "secretkey").compact();
            tokenMap.put("token", token);
            tokenMap.put("user", user);
            return new ResponseEntity<>(tokenMap, HttpStatus.OK);
        } else {
            tokenMap.put("token", null);
            return new ResponseEntity<>(tokenMap, HttpStatus.UNAUTHORIZED);
        }

    }
}
