package com.project.bff.application.mappings;

import com.project.bff.application.dtos.responses.BrasilApiCepClientResponse;
import com.project.bff.application.dtos.responses.CepServiceResponse;
import org.springframework.stereotype.Component;

@Component
public class BrasilApiCepClientResponseMapping {

    public CepServiceResponse convertToCepServiceResponse(BrasilApiCepClientResponse response) {
        return new CepServiceResponse(response.getCep(),
                response.getStreet(),
                "",
                response.getNeighborhood(),
                response.getCity(),
                response.getState(),
                "",
                "",
                "",
                "");
    }
}
