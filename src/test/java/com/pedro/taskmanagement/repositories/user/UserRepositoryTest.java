package com.pedro.taskmanagement.repositories.user;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get User successfuly from DB")
    void findByEmailSucess() {
    }

}