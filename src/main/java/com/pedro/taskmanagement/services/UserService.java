package com.pedro.taskmanagement.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedro.taskmanagement.domain.User;
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
        return obj.get();
    }

    public User insert(User obj) {
        return repository.save(obj);
    }

    public User update(String id, User obj) {
        User entity = repository.getReferenceById(id);
        updatedData(entity, obj);
        return repository.save(entity);
    }

    private void updatedData(User entity, User obj) {
        entity.setName(obj.getName());
        entity.setUsername(obj.getUsername());
        entity.setPassword(obj.getPassword());
        entity.setEmail(obj.getEmail());
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
