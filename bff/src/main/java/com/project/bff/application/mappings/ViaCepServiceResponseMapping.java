package com.project.bff.application.mappings;

import org.springframework.stereotype.Component;

import com.project.bff.application.dtos.responses.CepServiceResponse;
import com.project.bff.application.dtos.responses.ViaCepServiceResponse;

@Component
public class ViaCepServiceResponseMapping {

    public CepServiceResponse convertToCepServiceResponse(ViaCepServiceResponse response) {
        return new CepServiceResponse(response.getCep(),
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