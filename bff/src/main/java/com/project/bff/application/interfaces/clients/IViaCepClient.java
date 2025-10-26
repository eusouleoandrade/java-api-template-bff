package com.project.bff.application.interfaces.clients;

import com.project.bff.application.dtos.responses.ViaCepClientResponse;

import java.util.concurrent.CompletableFuture;

public interface IViaCepClient {

    CompletableFuture<ViaCepClientResponse> getAddressAsync(String cep);
}
