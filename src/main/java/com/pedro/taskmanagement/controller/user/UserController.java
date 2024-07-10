package com.pedro.taskmanagement.controller.user;

import com.pedro.taskmanagement.domain.user.User;
import com.pedro.taskmanagement.dto.JwtTokenDTO;
import com.pedro.taskmanagement.dto.UserLoginDTO;
import com.pedro.taskmanagement.dto.UserRegisterDTO;
import com.pedro.taskmanagement.dto.UserResponseDTO;
import com.pedro.taskmanagement.infra.security.TokenService;
import com.pedro.taskmanagement.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    TokenService tokenService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listAllUsers() {
        List<User> list = service.findAllUsers();
        List<UserResponseDTO> listDto = list.stream().map(x -> new UserResponseDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> listUserById(@PathVariable String id) {
        User obj = service.findUserById(id);
        return ResponseEntity.ok().body(new UserResponseDTO(obj));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserRegisterDTO obj) {
        User user = service.createUser(obj);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> authenticateUser(@RequestBody @Valid UserLoginDTO obj) {
        JwtTokenDTO token = service.authenticateUser(obj);
        return ResponseEntity.ok().body(token);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody User obj) {
        service.updateUserById(id, obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
