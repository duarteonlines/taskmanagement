package com.pedro.taskmanagement.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedro.taskmanagement.domain.User;
import com.pedro.taskmanagement.dto.UserRegisterDTO;
import com.pedro.taskmanagement.exception.ObjectNotFoundException;
import com.pedro.taskmanagement.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(String id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public User insert(UserRegisterDTO obj) {
        User user = new User(null, obj.getName(), obj.getUsername(), obj.getEmail(), obj.getPassword());
        return user;
    }

    public User update(String id, User obj) {
        User entity = repository.getReferenceById(id);
        entity.setName(obj.getName());
        entity.setUsername(obj.getUsername());
        entity.setPassword(obj.getPassword());
        entity.setEmail(obj.getEmail());
        return repository.save(entity);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
