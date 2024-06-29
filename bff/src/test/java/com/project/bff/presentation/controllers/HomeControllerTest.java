package com.project.bff.presentation.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HomeControllerTest {

    private HomeController controller;

    @DisplayName("Test get success")
    @Test
    public void testGetSuccess() {

        // Arranje
        controller = new HomeController();

        // Act
        var response = controller.home();

        // Assert
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo("redirect:/swagger-ui.html");
    }
}
