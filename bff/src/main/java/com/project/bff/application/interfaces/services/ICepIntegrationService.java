package com.project.bff.application.interfaces.services;

import java.util.concurrent.CompletableFuture;

import com.project.bff.application.dtos.responses.CepIntegrationServiceResponse;

public interface ICepIntegrationService {

    CompletableFuture<CepIntegrationServiceResponse> getAddressAsync(String cep);
}