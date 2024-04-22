package com.project.bff.application.useCases;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import com.project.bff.application.dtos.responses.GetAddressUseCaseResponse;
import com.project.bff.application.interfaces.repositories.IAddressAuditRepositoryAsync;
import com.project.bff.application.interfaces.useCases.IGetAddressUseCase;
import com.project.bff.domain.entities.AddressAudit;
import com.project.bff.shared.notifications.abstractions.Notifiable;
import com.project.bff.shared.ultils.MsgUltil;

@Service
@RequestScope
public class GetAddressUseCase extends Notifiable implements IGetAddressUseCase {

    private final IAddressAuditRepositoryAsync addressAuditRepositoryAsync;
    private final Logger logger = LoggerFactory.getLogger(GetAddressUseCase.class);

    @Autowired
    private Environment env;

    public GetAddressUseCase(IAddressAuditRepositoryAsync addressAuditRepositoryAsync) {

        this.addressAuditRepositoryAsync = addressAuditRepositoryAsync;
    }

    @Override
    public CompletableFuture<GetAddressUseCaseResponse> runAsync(String request) {

        logger.info(String.format("Start useCase %s > method runAsync.",
                GetAddressUseCase.class.getSimpleName()));

        // Sanitize
        request = sanitize(request);

        // Validade
        validate(request);

        if (hasErrorNotification())
            return CompletableFuture.completedFuture(null);

        // Integrations

        // Audit
        var addressAudit = new AddressAudit(request, LocalDateTime.now());
        addressAuditRepositoryAsync.createAsync(addressAudit).join();

        var useCaseResponse = new GetAddressUseCaseResponse("cep", "logradouro", "bairro", "localidade", "estado");

        logger.info(String.format("Finishes successfully useCase  %s > method runAsync.",
                GetAddressUseCase.class.getSimpleName()));

        return CompletableFuture.completedFuture(useCaseResponse);
    }

    private String sanitize(String cep) {

        return cep.replaceAll("[^0-9]", "");
    }

    private void validate(String cep) {

        if (cep == null || cep.trim().isEmpty()) {
            addErrorNotification(MsgUltil.X0_IS_REQUIRED(null)[0], MsgUltil.X0_IS_REQUIRED("Cep")[1]);
        }

        int numberOfCharacters = Integer.valueOf(env.getProperty("cep.numberOfCharacters"));

        if (cep.length() != numberOfCharacters) {
            addErrorNotification(MsgUltil.X0_MUST_CONTAIN_X1_CHARACTERS(null, null)[0],
                    MsgUltil.X0_MUST_CONTAIN_X1_CHARACTERS("Cep", String.valueOf(numberOfCharacters))[1]);
        }
    }
}