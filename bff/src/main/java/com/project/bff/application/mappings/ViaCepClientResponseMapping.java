package com.project.bff.application.mappings;

import com.project.bff.application.dtos.responses.CepServiceResponse;
import com.project.bff.application.dtos.responses.ViaCepClientResponse;

import org.springframework.stereotype.Component;

@Component
public class ViaCepClientResponseMapping {

    public CepServiceResponse convertToCepServiceResponse(ViaCepClientResponse response) {

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
