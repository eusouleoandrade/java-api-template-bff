package com.project.bff.application.interfaces.repositories;

import java.util.concurrent.CompletableFuture;

import com.project.bff.domain.entities.AddressAudit;

public interface IAddressAuditRepositoryAsync {

    CompletableFuture<Void> createAsync(AddressAudit entity);
}