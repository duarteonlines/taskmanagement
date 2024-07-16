package com.pedro.taskmanagement.User.controller;

import com.pedro.taskmanagement.Security.authentication.TokenService;
import com.pedro.taskmanagement.User.dtos.*;
import com.pedro.taskmanagement.User.model.User;
import com.pedro.taskmanagement.User.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users", produces = {"application/json"})
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    TokenService tokenService;


    @Operation(summary = "Search for all registered users", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public ResponseEntity<List<UserListDTO>> listAllUsers() {
        List<User> list = service.findAllUsers();
        List<UserListDTO> listDto = list.stream().map(x -> new UserListDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }


    @Operation(summary = "Search for a user by specific id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List all tasks"),
            @ApiResponse(responseCode = "400", description = "Invalid request params"),
            @ApiResponse(responseCode = "401", description = "Not Authenticated"),
            @ApiResponse(responseCode =  "403", description = "Not Authorized"),
            @ApiResponse(responseCode = "500", description = "Internal Sever Error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserListDTO> listUserById(@PathVariable("id") UUID id) {
        User obj = service.findUserById(id);
        return ResponseEntity.ok().body(new UserListDTO(obj));
    }


    @Operation(summary = "Create a new user", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "401", description = "Not Authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")

    })
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserCreateDTO obj) {
        User user = service.createUser(obj);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Authenticate a user", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "401", description = "Not Authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")

    })
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtTokenDTO> authenticateUser(@RequestBody() @Valid UserAuthenticateDTO obj) {
        JwtTokenDTO token = service.authenticateUser(obj);
        return ResponseEntity.ok().body(token);
    }



    @Operation(summary = "Update a user", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "401", description = "Not Authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")

    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateAdmin(@PathVariable UUID id, @RequestBody @Valid UserUpdateDTO obj) {
        service.updateUserById(id, obj);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Delete a user", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")

    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Assign Task for User", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task assigned to the user"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PatchMapping
    public ResponseEntity<Void> assignTask(@RequestBody @Valid UserAssignTaskDTO obj){
        service.assignTask(obj);
        return ResponseEntity.noContent().build();
    }
}
