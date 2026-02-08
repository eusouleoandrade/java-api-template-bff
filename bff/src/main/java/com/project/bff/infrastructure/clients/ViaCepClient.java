package com.project.bff.infrastructure.clients;

import com.project.bff.application.dtos.responses.ViaCepClientResponse;
import com.project.bff.application.exceptions.AppException;
import com.project.bff.application.interfaces.clients.IViaCepClient;
import com.project.bff.shared.ultils.MsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class ViaCepClient implements IViaCepClient {

    private final RestTemplate restTemplate;
    private final String urlBase;
    private final Logger logger = LoggerFactory.getLogger(ViaCepClient.class);

    public ViaCepClient(Environment env, RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
        this.urlBase = env.getProperty("viaCepClient.urlBase");
    }

    @Override
    public CompletableFuture<ViaCepClientResponse> getAddressAsync(String cep) {

        logger.info("Start integration {} > method getAddressAsync.", ViaCepClient.class.getSimpleName());

        var fullUrl = String.format("%s/ws/%s/json", urlBase, cep);

        try {

            logger.info("Send integration {} > {}.", ViaCepClient.class.getSimpleName(), fullUrl);

            var viaCepClientResponse = restTemplate.getForObject(fullUrl, ViaCepClientResponse.class);

            return CompletableFuture.completedFuture(viaCepClientResponse);

        } catch (Exception ex) {

            logger.error("{} - Error: {}", MsgUtil.FAILED_TO_INTEGRATE_WITH_X0("ViaCep API")[1], ex.getMessage(), ex);

            throw new AppException(MsgUtil.FAILED_TO_INTEGRATE_WITH_X0("ViaCep API")[0], MsgUtil.FAILED_TO_INTEGRATE_WITH_X0("ViaCep API")[1], ex);

        } finally {

            logger.info("Finishes integration {} > method getAddressAsync.", ViaCepClient.class.getSimpleName());
        }
    }
}
