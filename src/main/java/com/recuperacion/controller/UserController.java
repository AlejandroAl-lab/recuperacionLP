package com.recuperacion.controller;

import com.recuperacion.model.User;
import com.recuperacion.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping
    public List<User> getAll() { return userService.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) { return ResponseEntity.ok(userService.findById(id)); }

    @PostMapping
    public User create(@RequestBody User user) { return userService.save(user); }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
