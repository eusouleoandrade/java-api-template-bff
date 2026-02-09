package com.project.bff.infrastructure.clients;

import com.project.bff.application.dtos.responses.BrasilApiCepClientResponse;
import com.project.bff.application.exceptions.AppException;
import com.project.bff.application.interfaces.clients.IBrasilApiCepClient;
import com.project.bff.shared.utils.MsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class BrasilApiCepClient implements IBrasilApiCepClient {

    private final RestTemplate restTemplate;
    private final String urlBase;
    private final Logger logger = LoggerFactory.getLogger(BrasilApiCepClient.class);

    public BrasilApiCepClient(Environment env, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.urlBase = env.getProperty("brasilApiClient.urlBase");
    }

    @Override
    public CompletableFuture<BrasilApiCepClientResponse> getAddressAsync(String cep) {

        logger.info("Start integration {} > method getAddressAsync.", BrasilApiCepClient.class.getSimpleName());

        var fullUrl = String.format("%s/api/cep/v2/%s", urlBase, cep);

        try {

            logger.info("Send integration {} > {}.", BrasilApiCepClient.class.getSimpleName(), fullUrl);

            var brasilApiCepClientResponse = restTemplate.getForObject(fullUrl, BrasilApiCepClientResponse.class);

            return CompletableFuture.completedFuture(brasilApiCepClientResponse);

        } catch (Exception ex) {

            logger.error("{} - Error: {}", MsgUtil.FAILED_TO_INTEGRATE_WITH_X0("BrasilCep API")[1], ex.getMessage(), ex);

            throw new AppException(MsgUtil.FAILED_TO_INTEGRATE_WITH_X0("BrasilCep API")[0], MsgUtil.FAILED_TO_INTEGRATE_WITH_X0("BrasilCep API")[1], ex);

        } finally {

            logger.info("Finishes integration {} > method getAddressAsync.", BrasilApiCepClient.class.getSimpleName());
        }
    }
}
