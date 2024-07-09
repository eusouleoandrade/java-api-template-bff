package com.project.bff.infrastructure.persistence.repositories;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.bff.application.exceptions.AppException;
import com.project.bff.application.interfaces.repositories.IAddressAuditRepositoryAsync;
import com.project.bff.domain.entities.AddressAudit;
import com.project.bff.shared.ultils.MsgUltil;

@Repository
public class AddressAuditRepositoryAsync implements IAddressAuditRepositoryAsync {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(AddressAuditRepositoryAsync.class);

    public AddressAuditRepositoryAsync(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {

        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public CompletableFuture<Void> createAsync(AddressAudit entity) {

        logger.info(String.format("Start repository %s > method createAsync.",
                AddressAuditRepositoryAsync.class.getSimpleName()));

        String query = "INSERT INTO bffdb.addressAudit (cep, dataHora) VALUES (:cep, :dataHora)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("cep", entity.getCep())
                .addValue("dataHora", entity.getDataHora());

        try {

            int affectedLines = namedParameterJdbcTemplate.update(query, params);

            if (affectedLines > 0) {

                logger.info(String.format("Audit insertion completed successfully. Repository %s > method createAsync.",
                        AddressAuditRepositoryAsync.class.getSimpleName()));

            } else {

                logger.warn(String.format("Failed to insert. Zero rows affected. Repository %s > method createAsync.",
                        AddressAuditRepositoryAsync.class.getSimpleName()));
            }

            return CompletableFuture.completedFuture(null);

        } catch (Exception ex) {

            logger.error(MsgUltil.DATA_BASE_SERVER_ERROR()[1] + " - Error: " + ex.getMessage(), ex);

            throw new AppException(MsgUltil.DATA_BASE_SERVER_ERROR()[0], MsgUltil.DATA_BASE_SERVER_ERROR()[1], ex);

        } finally {

            logger.info(String.format("Finishes repository %s > method createAsync.",
                    AddressAuditRepositoryAsync.class.getSimpleName()));
        }
    }
}