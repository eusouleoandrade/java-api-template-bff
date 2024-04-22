package com.project.bff.application.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.bff.application.dtos.models.AddressModel;
import com.project.bff.application.dtos.wrappers.Response;

public class GetAddressResponse extends Response {

    @JsonProperty("data")
    private AddressModel addressModel;

    public GetAddressResponse(AddressModel addressModel) {
        super(true);

        this.addressModel = addressModel;
    }

    public AddressModel getAddressModel() {
        return addressModel;
    }
}