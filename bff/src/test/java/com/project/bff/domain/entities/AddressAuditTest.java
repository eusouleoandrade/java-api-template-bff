package com.project.bff.domain.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AddressAuditTest {

    @DisplayName("Test AddressAudit constructor with id")
    @Test
    public void testAddressAuditConstructorWithId() {

        // Arrange
        long id = 1L;
        String cep = "12345678";
        LocalDateTime dataHora = LocalDateTime.now();

        // Act
        AddressAudit addressAudit = new AddressAudit(id, cep, dataHora);

        // Assert
        assertThat(addressAudit).isNotNull();

        assertEquals(id, addressAudit.getId());
        assertEquals(cep, addressAudit.getCep());
        assertEquals(dataHora, addressAudit.getDataHora());
    }

    @DisplayName("Test AddressAudit constructor without id")
    @Test
    public void testAddressAuditConstructorWithoutId() {

        // Arrange
        String cep = "12345678";
        LocalDateTime dataHora = LocalDateTime.now();

        // Act
        AddressAudit addressAudit = new AddressAudit(cep, dataHora);

        // Assert
        assertThat(addressAudit).isNotNull();

        assertEquals(cep, addressAudit.getCep());
        assertEquals(dataHora, addressAudit.getDataHora());
    }

    @DisplayName("Test getCep")
    @Test
    public void testGetCep() {

        // Arrange
        String cep = "12345678";
        LocalDateTime dataHora = LocalDateTime.now();
        AddressAudit addressAudit = new AddressAudit(cep, dataHora);

        // Act
        String result = addressAudit.getCep();

        // Assert
        assertEquals(cep, result);
    }

    @DisplayName("Test getDataHora")
    @Test
    public void testGetDataHora() {

        // Arrange
        LocalDateTime dataHora = LocalDateTime.now();
        String cep = "12345678";
        AddressAudit addressAudit = new AddressAudit(cep, dataHora);

        // Act
        LocalDateTime result = addressAudit.getDataHora();

        // Assert
        assertEquals(dataHora, result);
    }

    @DisplayName("Test getId")
    @Test
    public void testGetId() {

        // Arrange
        long id = 1L;
        LocalDateTime dataHora = LocalDateTime.now();
        String cep = "12345678";
        AddressAudit addressAudit = new AddressAudit(id, cep, dataHora);

        // Act
        Long result = addressAudit.getId();

        // Assert
        assertEquals(id, result);
    }

    @DisplayName("Test setId")
    @Test
    public void testSetId() {

        // Arrange
        long id = 1L;
        LocalDateTime dataHora = LocalDateTime.now();
        String cep = "12345678";
        AddressAudit addressAudit = new AddressAudit(cep, dataHora);

        // Act
        addressAudit.setId(id);

        // Assert
        assertEquals(id, addressAudit.getId());
    }
}