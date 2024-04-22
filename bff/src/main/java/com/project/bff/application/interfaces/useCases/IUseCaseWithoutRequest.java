package com.project.bff.application.interfaces.useCases;

import java.util.concurrent.CompletableFuture;

public interface IUseCaseWithoutRequest<TResponse> {

    CompletableFuture<TResponse> runAsync();
}