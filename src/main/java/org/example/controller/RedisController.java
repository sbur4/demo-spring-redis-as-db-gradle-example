package org.example.controller;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class RedisController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/user/{fname}/{lname}")
    public void saveUser(@PathVariable String fname, @PathVariable String lname) {
        User user = new User(UUID.randomUUID().toString(), fname, lname);

        userRepository.save(user); // set + hash

        redisTemplate.opsForValue().set(user.getId(), user); // string
    }

    @GetMapping("/user/{id}")
    public List<User> getUser(@PathVariable String id) {
        User user1 = userRepository.findById(id).get();

        User user2 = (User) redisTemplate.opsForValue().get(id);

        return Stream.of(user1, user2).collect(Collectors.toList());
    }

    @PutMapping("/user/{id}&{fname}")
    public List<User> updateUser(@PathVariable String id, @PathVariable String fname) {
        User user1 = userRepository.findById(id).get();
        user1.setFirstName(fname);
        userRepository.save(user1);

        User user2 = (User) redisTemplate.opsForValue().get(id);
        redisTemplate.opsForValue().setIfPresent(user2.getId(), user2);

        return (List<User>) userRepository.findAll();
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);

        redisTemplate.delete(id);
    }
}