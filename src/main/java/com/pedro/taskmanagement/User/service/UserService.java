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
        checkExistenceUniqueData(obj);
        User user = new User();
        createUserData(user, obj);
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
        
        validateExistenceUser(user);
        updateDataUser(user, obj);
        User updatedUser = user.get();
        
        repository.save(updatedUser);
    }

    public void deleteUserById(UUID id) {
        Optional<User> user = repository.findById(id);
        validateExistenceUser(user);
        repository.deleteById(id);
    }

    public void assignTask(UserAssignTaskDTO obj) {
        UUID userId = UUID.fromString(obj.getUser());
        UUID taskId = UUID.fromString(obj.getTask());

        Optional<User> existsUser = repository.findById(userId);
        Optional<Task> existsTask = taskRepository.findById(taskId);

        validateExistenceAssignment(existsTask, existsUser);

        Task task = existsTask.get();
        User user = existsUser.get();

        validateTaskAssignment(task);
        validateUserAssignment(user);
        assignTaskForUser(task, user);
        
        repository.save(user);
    }

    private void createUserData(User user, UserCreateDTO obj) {
        user.setName(obj.getName());
        user.setUsername(obj.getUsername());
        user.setPassword(obj.getPassword());
        user.setEmail(obj.getEmail());
        user.getRoles().add(new Role(null, RoleName.ROLE_EMPLOYEE));
        user.setTask(null);
    }

    private void checkExistenceUniqueData(UserCreateDTO obj) {
        Optional<User> user = repository.findByEmail(obj.getEmail());
        if (user.isPresent()) {
            throw new AlreadyExistsException("Email already exists");
        }
    }
    
    private void updateDataUser(Optional<User> user, UserUpdateDTO data){
        user.get().setName(data.getName());
        user.get().setUsername(data.getUsername());
        user.get().setPassword(passwordEncoder.encode(data.getPassword()));
        user.get().setEmail(data.getEmail());
    }

    private void validateExistenceUser(Optional<User> user) {
        if (user.isEmpty()) {
            throw new ObjectNotFoundException("User not found!");
        }
    }

    private void validateExistenceAssignment(Optional<Task> task, Optional<User> user) {
        if (task.isEmpty()) {
            throw new ObjectNotFoundException("Task not found!");
        }
        if (user.isEmpty()) {
            throw new ObjectNotFoundException("User not found!");
        }
    }

    private void validateTaskAssignment(Task task) {
        if (task.getUser() != null) {
            throw new AlreadyExistsException("This task already belongs to a user");
        }
    }

    private void validateUserAssignment(User user) {
        if (user.getTask() != null) {
            throw new AlreadyExistsException("This user already has a task assigned");
        }
    }

    private void assignTaskForUser(Task task, User user) {
        task.setUser(user);
        user.setTask(task);
    }

}
