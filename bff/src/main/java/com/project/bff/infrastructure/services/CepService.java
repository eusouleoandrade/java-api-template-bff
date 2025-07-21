package com.project.bff.infrastructure.services;

import com.project.bff.application.dtos.responses.CepServiceResponse;
import com.project.bff.application.interfaces.services.ICepService;

import java.util.concurrent.CompletableFuture;

public class CepService implements ICepService {

    @Override
    public CompletableFuture<CepServiceResponse> getAddressAsync(String cep) {
        return null;
    }
}