package com.project.bff.application.mappings;

import org.springframework.stereotype.Component;

import com.project.bff.application.dtos.responses.CepServiceResponse;
import com.project.bff.application.dtos.responses.GetAddressUseCaseResponse;

@Component
public class CepServiceResponseMapping {

    public GetAddressUseCaseResponse convertToGetAddressUseCaseResponse(CepServiceResponse response) {

        return new GetAddressUseCaseResponse(response.getCep(),
                response.getLogradouro(),
                response.getComplemento(),
                response.getBairro(),
                response.getLocalidade(),
                response.getUf(),
                response.getIbge(),
                response.getGia(),
                response.getDdd(),
                response.getSiafi());
    }
}