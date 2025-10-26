package com.project.bff.infrastructure.clients;

import com.project.bff.application.dtos.responses.ViaCepClientResponse;
import com.project.bff.application.interfaces.clients.IViaCepClient;

import java.util.concurrent.CompletableFuture;

public class ViaCepClient implements IViaCepClient {

    @Override
    public CompletableFuture<ViaCepClientResponse> getAddressAsync(String cep) {

        // Todo: Continuar daaqui.
        return null;
    }
}
