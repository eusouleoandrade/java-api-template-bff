package com.project.bff.infrastructure.integrations.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.project.bff.application.dtos.responses.CepIntegrationServiceResponse;
import com.project.bff.application.interfaces.ICepIntegrationService;

@Service
public class ViaCepIntegrationService implements ICepIntegrationService {

    @Override
    public CompletableFuture<CepIntegrationServiceResponse> getAddressAsync(String cep) {

        throw new UnsupportedOperationException("Unimplemented method 'getAddress'");
    }
}