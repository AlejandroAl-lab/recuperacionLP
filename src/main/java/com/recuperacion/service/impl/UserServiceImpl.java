package com.recuperacion.service.impl;

import com.recuperacion.model.User;
import com.recuperacion.repository.UserRepository;
import com.recuperacion.service.UserService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() { return userRepository.findAll(); }

    @Override
    public User findById(Long id) { return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado")); }

    @Override
    public User save(User entity) {
        if(entity.getSalt() == null) entity.setSalt("default_salt");
        return userRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) { userRepository.deleteById(id); }
}
