package com.pedro.taskmanagement.domain.role;

import com.pedro.taskmanagement.enums.role.RoleName;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "roles")
public class Role implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private RoleName role;

    public Role() {

    }

    public Role(UUID id, RoleName role) {
        this.id = id;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }

}
