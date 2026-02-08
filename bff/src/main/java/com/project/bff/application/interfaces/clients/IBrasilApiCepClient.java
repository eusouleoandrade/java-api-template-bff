package com.project.bff.application.interfaces.clients;

import com.project.bff.application.dtos.responses.BrasilApiCepClientResponse;

import java.util.concurrent.CompletableFuture;

public interface IBrasilApiCepClient {

    CompletableFuture<BrasilApiCepClientResponse> getAddressAsync(String cep);
}
