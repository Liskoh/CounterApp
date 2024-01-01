package me.liskoh.counter.services;

import me.liskoh.counter.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
public class UserService {

//    private final UserRepository userRepository;
//
//    @Autowired
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    public String hello(String username) {
        if (username == null) {
            return "Hello World!";
        }

        return "Hello " + username + "!";
    }
}
