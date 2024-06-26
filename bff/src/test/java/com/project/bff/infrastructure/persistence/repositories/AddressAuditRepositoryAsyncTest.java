package com.project.bff.infrastructure.persistence.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.project.bff.application.exceptions.AppException;
import com.project.bff.domain.entities.AddressAudit;
import com.project.bff.shared.ultils.MsgUltil;

import nl.altindag.log.LogCaptor;

@SpringBootTest
public class AddressAuditRepositoryAsyncTest {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private AddressAuditRepositoryAsync addressAuditRepositoryAsync;

    private AddressAudit addressAudit;

    private LogCaptor logCaptor;

    public AddressAuditRepositoryAsyncTest() {

        namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
    }

    @BeforeEach
    public void setup() {

        addressAudit = new AddressAudit("12345678", LocalDateTime.now());
    }

    @DisplayName("Test CreateAsync success")
    @Test
    public void testCreateAsyncSuccess() {

        // Arrange
        when(namedParameterJdbcTemplate.update(any(String.class), any(MapSqlParameterSource.class))).thenReturn(1);

        addressAuditRepositoryAsync = new AddressAuditRepositoryAsync(namedParameterJdbcTemplate);

        logCaptor = LogCaptor.forClass(AddressAuditRepositoryAsync.class);

        // Act
        addressAuditRepositoryAsync.createAsync(addressAudit).join();

        // Assert
        assertThat(addressAuditRepositoryAsync).isNotNull();

        verify(namedParameterJdbcTemplate, times(1)).update(any(String.class), any(MapSqlParameterSource.class));

        assertThat(logCaptor.getInfoLogs())
                .containsExactly(
                        "Start repository AddressAuditRepositoryAsync > method createAsync.",
                        "Audit insertion completed successfully. Repository AddressAuditRepositoryAsync > method createAsync.",
                        "Finishes repository AddressAuditRepositoryAsync > method createAsync.");
    }

    @DisplayName("Test CreateAsync failure with zero rows affected")
    @Test
    public void testCreateAsyncFailureWithZeroRowsAffected() {

        // Arrange
        when(namedParameterJdbcTemplate.update(any(String.class), any(MapSqlParameterSource.class))).thenReturn(0);

        addressAuditRepositoryAsync = new AddressAuditRepositoryAsync(namedParameterJdbcTemplate);

        logCaptor = LogCaptor.forClass(AddressAuditRepositoryAsync.class);

        // Act
        addressAuditRepositoryAsync.createAsync(addressAudit).join();

        // Assert
        assertThat(addressAuditRepositoryAsync).isNotNull();

        verify(namedParameterJdbcTemplate, times(1)).update(any(String.class), any(MapSqlParameterSource.class));

        assertThat(logCaptor.getInfoLogs())
                .containsExactly(
                        "Start repository AddressAuditRepositoryAsync > method createAsync.",
                        "Finishes repository AddressAuditRepositoryAsync > method createAsync.");

        assertThat(logCaptor.getWarnLogs())
                .containsExactly(
                        "Failed to insert. Zero rows affected. Repository AddressAuditRepositoryAsync > method createAsync.");
    }

    @DisplayName("Test CreateAsync failure with exception")
    @Test
    public void testCreateAsyncFailureWithException() {

        // Arrange
        when(namedParameterJdbcTemplate.update(any(String.class), any(MapSqlParameterSource.class)))
                .thenThrow(new RuntimeException("Database error"));

        addressAuditRepositoryAsync = new AddressAuditRepositoryAsync(namedParameterJdbcTemplate);

        logCaptor = LogCaptor.forClass(AddressAuditRepositoryAsync.class);

        // Act & Assert
        assertThrows(AppException.class, () -> addressAuditRepositoryAsync.createAsync(addressAudit).join());

        assertThat(addressAuditRepositoryAsync).isNotNull();

        verify(namedParameterJdbcTemplate, times(1)).update(any(String.class), any(MapSqlParameterSource.class));

        assertThat(logCaptor.getInfoLogs())
                .containsExactly(
                        "Start repository AddressAuditRepositoryAsync > method createAsync.",
                        "Finishes repository AddressAuditRepositoryAsync > method createAsync.");

        assertThat(logCaptor.getErrorLogs())
                .containsExactly(
                        MsgUltil.DATA_BASE_SERVER_ERROR()[1] + " - Error: Database error");
    }
}