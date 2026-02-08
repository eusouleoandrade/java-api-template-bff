package com.project.bff.infrastructure.services;

import com.project.bff.application.dtos.responses.CepServiceResponse;
import com.project.bff.application.exceptions.AppException;
import com.project.bff.application.interfaces.clients.IBrasilApiCepClient;
import com.project.bff.application.interfaces.clients.IViaCepClient;
import com.project.bff.application.interfaces.services.ICepService;
import com.project.bff.application.mappings.BrasilApiCepClientResponseMapping;
import com.project.bff.application.mappings.ViaCepClientResponseMapping;
import com.project.bff.shared.ultils.MsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class CepService implements ICepService {

    private final Logger logger = LoggerFactory.getLogger(CepService.class);
    private final IViaCepClient viaCepClient;
    private final ViaCepClientResponseMapping viaCepClientResponseMapping;
    private final IBrasilApiCepClient brasilApiCepClient;
    private final BrasilApiCepClientResponseMapping brasilApiCepClientResponseMapping;

    public CepService(IViaCepClient viaCepClient, ViaCepClientResponseMapping viaCepClientResponseMapping, IBrasilApiCepClient brasilApiCepClient, BrasilApiCepClientResponseMapping brasilApiCepClientResponseMapping) {

        this.viaCepClient = viaCepClient;
        this.viaCepClientResponseMapping = viaCepClientResponseMapping;
        this.brasilApiCepClient = brasilApiCepClient;
        this.brasilApiCepClientResponseMapping = brasilApiCepClientResponseMapping;
    }

    @Override
    public CompletableFuture<CepServiceResponse> getAddressAsync(String cep) {

        logger.info("Start service {} > method getAddressAsync.", CepService.class.getSimpleName());

        try {

            // First attempt: ViaCep
            try {
                var viaCepClientResponse = viaCepClient.getAddressAsync(cep).join();
                var response = viaCepClientResponseMapping.convertToCepServiceResponse(viaCepClientResponse);

                if (response != null) {
                    logger.info("Finish service {} > Successfully got address for CEP {} using ViaCep.", CepService.class.getSimpleName(), cep);
                    return CompletableFuture.completedFuture(response);
                } else {
                    logger.warn("{} - ViaCep returned null for CEP {}. Will try BrasilCep API.", MsgUtil.SERVICE_FAILURE_X0("ViaCep API")[1], cep);
                }

            } catch (Exception ex) {
                logger.error("{} - Error: {}", MsgUtil.SERVICE_FAILURE_X0("ViaCep API")[1], ex.getMessage(), ex);
                logger.info("Start service {} > method getAddressAsync - Trying BrasilCep API.", CepService.class.getSimpleName());
            }

            // Fallback: BrasilApiCep
            try {
                var brasilApiCepClientResponse = brasilApiCepClient.getAddressAsync(cep).join();
                var response = brasilApiCepClientResponseMapping.convertToCepServiceResponse(brasilApiCepClientResponse);

                if (response != null) {
                    logger.info("Finish service {} > Successfully got address for CEP {} using BrasilCep.", CepService.class.getSimpleName(), cep);
                    return CompletableFuture.completedFuture(response);
                } else {
                    logger.error("{} - BrasilCep returned null for CEP {}.", MsgUtil.SERVICE_FAILURE_X0("BrasilCep API")[1], cep);
                    throw new AppException(MsgUtil.SERVICE_FAILURE_X0("BrasilCep API")[0], MsgUtil.SERVICE_FAILURE_X0("BrasilCep API")[1], null);
                }

            } catch (Exception exBrasil) {
                logger.error("{} - Error: {}", MsgUtil.SERVICE_FAILURE_X0("BrasilCep API")[1], exBrasil.getMessage(), exBrasil);
                throw new AppException(MsgUtil.SERVICE_FAILURE_X0("BrasilCep API")[0], MsgUtil.SERVICE_FAILURE_X0("BrasilCepss API")[1], exBrasil);
            }

        } finally {
            logger.info("Finishes service {} > method getAddressAsync.", CepService.class.getSimpleName());
        }
    }
}