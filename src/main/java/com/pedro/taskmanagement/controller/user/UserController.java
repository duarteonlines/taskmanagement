package com.pedro.taskmanagement.controller.user;

import com.pedro.taskmanagement.domain.user.User;
import com.pedro.taskmanagement.dto.JwtTokenDTO;
import com.pedro.taskmanagement.dto.UserLoginDTO;
import com.pedro.taskmanagement.dto.UserRegisterDTO;
import com.pedro.taskmanagement.dto.UserResponseDTO;
import com.pedro.taskmanagement.infra.security.jwt.TokenService;
import com.pedro.taskmanagement.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/users", produces = {"application/json"})
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    TokenService tokenService;

    @GetMapping()
    @Operation(summary = "Search for all registered users", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<List<UserResponseDTO>> listAllUsers() {
        List<User> list = service.findAllUsers();
        List<UserResponseDTO> listDto = list.stream().map(x -> new UserResponseDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Search for a user by specific id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<UserResponseDTO> listUserById(@PathVariable("id") UUID id) {
        User obj = service.findUserById(id);
        return ResponseEntity.ok().body(new UserResponseDTO(obj));
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Register a user", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")

    })
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserRegisterDTO obj) {
        User user = service.createUser(obj);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Authenticate a user", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")

    })
    public ResponseEntity<JwtTokenDTO> authenticateUser(@RequestBody() @Valid UserLoginDTO obj) {
        JwtTokenDTO token = service.authenticateUser(obj);
        return ResponseEntity.ok().body(token);
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update a user", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")

    })
    public ResponseEntity<Void> updateAdmin(@PathVariable UUID id, @RequestBody User obj) {
        service.updateUserById(id, obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Authenticate a user", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")

    })
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
