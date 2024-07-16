package com.pedro.taskmanagement.User.service;

import com.pedro.taskmanagement.Exceptions.exceptions.AlreadyExistsException;
import com.pedro.taskmanagement.Exceptions.exceptions.ObjectNotFoundException;
import com.pedro.taskmanagement.Security.authentication.TokenService;
import com.pedro.taskmanagement.Security.userdetails.UserDetailsImpl;
import com.pedro.taskmanagement.Task.model.Role;
import com.pedro.taskmanagement.Task.model.Task;
import com.pedro.taskmanagement.Task.repository.TaskRepository;
import com.pedro.taskmanagement.User.dtos.*;
import com.pedro.taskmanagement.User.enums.RoleName;
import com.pedro.taskmanagement.User.model.User;
import com.pedro.taskmanagement.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TaskRepository taskRepository;

    public List<User> findAllUsers() {
        return repository.findAll();
    }

    public User findUserById(UUID id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("User Not Found"));
    }

    public User createUser(UserCreateDTO obj) {
        Optional<User> existUser = repository.findByEmail(obj.getEmail());
        if (existUser.isPresent()) {
            throw new AlreadyExistsException("Email already exists");
        }
        User user = new User(null, obj.getName(), obj.getUsername(), passwordEncoder.encode(obj.getPassword()),
                obj.getEmail(), null);
        user.getRoles().add(new Role(null, RoleName.ROLE_EMPLOYEE));
        return repository.save(user);
    }

    public JwtTokenDTO authenticateUser(UserAuthenticateDTO obj) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                obj.getEmail(), obj.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new JwtTokenDTO(tokenService.generateToken(userDetails));
    }

    public void updateUserById(UUID id, UserUpdateDTO obj) {
        Optional<User> user = repository.findById(id);
        if(user.isEmpty()){
            throw new ObjectNotFoundException("User Not Found");
        }
        user.get().setName(obj.getName());
        user.get().setUsername(obj.getUsername());
        user.get().setPassword(passwordEncoder.encode(obj.getPassword()));
        user.get().setEmail(obj.getEmail());
        repository.save(user.get());
    }

    public void deleteUserById(UUID id) {
        repository.deleteById(id);
    }

    public void assignTask(UserAssignTaskDTO obj){
        Optional<User> existsUser = repository.findById(obj.getUser());
        Optional<Task> existsTask = taskRepository.findById(obj.getTask());

        if(existsTask.isEmpty() || existsUser.isEmpty()) {
            throw new ObjectNotFoundException("User or Task not found!");
        }

        User user = existsUser.get();
        Task task = existsTask.get();
        user.setTask(task);
        task.setUser(user);

        repository.save(user);
        taskRepository.save(task);
    }
}
