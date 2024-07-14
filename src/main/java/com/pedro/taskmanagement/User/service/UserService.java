package com.pedro.taskmanagement.User.service;

import com.pedro.taskmanagement.Task.model.Role;
import com.pedro.taskmanagement.User.dtos.UserUpdateDTO;
import com.pedro.taskmanagement.User.model.User;
import com.pedro.taskmanagement.Security.userdetails.UserDetailsImpl;
import com.pedro.taskmanagement.User.dtos.JwtTokenDTO;
import com.pedro.taskmanagement.User.dtos.UserAuthenticateDTO;
import com.pedro.taskmanagement.User.dtos.UserCreateDTO;
import com.pedro.taskmanagement.User.enums.RoleName;
import com.pedro.taskmanagement.Exceptions.exceptions.AlreadyExistsException;
import com.pedro.taskmanagement.Exceptions.exceptions.ObjectNotFoundException;
import com.pedro.taskmanagement.Security.authentication.TokenService;
import com.pedro.taskmanagement.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    AuthenticationManager authenticationManager;
    @Autowired
    TokenService tokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAllUsers() {
        return repository.findAll();
    }

    public User findUserById(UUID id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("User Not Found"));
    }

    public User createUser(UserCreateDTO obj) {
        Optional<User> existUser = repository.findByEmail(obj.getUsername());
        System.out.println(existUser);
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
        var authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new JwtTokenDTO(tokenService.generateToken(userDetails));
    }

    public User updateUserById(UUID id, UserUpdateDTO obj) {
        User entity = repository.getReferenceById(id);
        entity.setName(obj.getName());
        entity.setUsername(obj.getUsername());
        entity.setPassword(obj.getPassword());
        entity.setEmail(obj.getEmail());
        return repository.save(entity);
    }

    public void deleteUserById(UUID id) {
        repository.deleteById(id);
    }
}
