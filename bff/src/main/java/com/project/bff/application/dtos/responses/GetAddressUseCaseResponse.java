package com.project.bff.application.dtos.responses;

public class GetAddressUseCaseResponse {

    private String cep;

    private String logradouro;

    private String bairro;

    private String localidade;

    private String estado;

    public GetAddressUseCaseResponse(String cep, String logradouro, String bairro, String localidade, String estado) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.localidade = localidade;
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public String getEstado() {
        return estado;
    }
}