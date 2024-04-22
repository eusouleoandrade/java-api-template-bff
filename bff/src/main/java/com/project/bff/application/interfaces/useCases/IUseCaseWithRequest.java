package com.project.bff.application.interfaces.useCases;

import java.util.concurrent.CompletableFuture;

public interface IUseCaseWithRequest<TRequest, TResponse> {

    CompletableFuture<TResponse> runAsync(TRequest request);
}