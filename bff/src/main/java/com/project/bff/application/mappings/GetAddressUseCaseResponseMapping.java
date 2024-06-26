package com.project.bff.application.mappings;

import org.springframework.stereotype.Component;

import com.project.bff.application.dtos.models.AddressModel;
import com.project.bff.application.dtos.responses.GetAddressUseCaseResponse;

@Component
public class GetAddressUseCaseResponseMapping {

    public AddressModel convertToAddressModel(GetAddressUseCaseResponse response) {

        return new AddressModel(response.getCep(),
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