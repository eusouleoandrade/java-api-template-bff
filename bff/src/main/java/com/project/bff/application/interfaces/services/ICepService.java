package com.project.bff.application.interfaces.services;

import java.util.concurrent.CompletableFuture;

import com.project.bff.application.dtos.responses.CepServiceResponse;

public interface ICepService {

    CompletableFuture<CepServiceResponse> getAddressAsync(String cep);
}