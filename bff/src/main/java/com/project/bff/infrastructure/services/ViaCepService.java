package com.project.bff.infrastructure.services;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.project.bff.application.dtos.responses.CepServiceResponse;
import com.project.bff.application.dtos.responses.ViaCepServiceResponse;
import com.project.bff.application.exceptions.AppException;
import com.project.bff.application.interfaces.services.ICepService;
import com.project.bff.application.mappings.ViaCepServiceResponseMapping;
import com.project.bff.shared.ultils.MsgUltil;

@Service
public class ViaCepService implements ICepService {

    private final RestTemplate restTemplate;

    private final String urlBase;

    private final ViaCepServiceResponseMapping viaCepServiceResponseMapping;

    private final Logger logger = LoggerFactory.getLogger(ViaCepService.class);

    public ViaCepService(ViaCepServiceResponseMapping viaCepServiceResponseMapping,
            Environment env,
            RestTemplate restTemplate) {

        this.viaCepServiceResponseMapping = viaCepServiceResponseMapping;
        this.restTemplate = restTemplate;

        urlBase = env.getProperty("viaCepService.urlBase");
    }

    @Override
    public CompletableFuture<CepServiceResponse> getAddressAsync(String cep) {

        logger.info(String.format("Start integration %s > method getAddressAsync.",
                ViaCepService.class.getSimpleName()));

        var fullUrl = String.format("%s/ws/%s/json", urlBase, cep);

        try {

            logger.info(String.format("Send integration %s > %s.", ViaCepService.class.getSimpleName(), fullUrl));

            var viaCepServiceResponse = restTemplate.getForObject(fullUrl, ViaCepServiceResponse.class);

            var response = viaCepServiceResponseMapping.convertToCepServiceResponse(viaCepServiceResponse);

            return CompletableFuture.completedFuture(response);

        } catch (Exception ex) {

            logger.error(MsgUltil.FAILED_TO_INTEGRATE_WITH_X0("ViaCep API")[1] + " - Error: " + ex.getMessage(), ex);

            throw new AppException(MsgUltil.FAILED_TO_INTEGRATE_WITH_X0("ViaCep API")[0],
                    MsgUltil.FAILED_TO_INTEGRATE_WITH_X0("ViaCep API")[1], ex);

        } finally {

            logger.info(String.format("Finishes integration %s > method getAddressAsync.",
                    ViaCepService.class.getSimpleName()));
        }
    }
}