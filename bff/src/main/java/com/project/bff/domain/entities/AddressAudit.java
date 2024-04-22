package com.project.bff.domain.entities;

import java.time.LocalDateTime;

import com.project.bff.domain.common.BaseEntity;

public class AddressAudit extends BaseEntity<Long> {

    private String cep;

    private LocalDateTime dataHora;

    public AddressAudit(long id, String cep, LocalDateTime dataHora) {

        this.id = id;
        this.cep = cep;
        this.dataHora = dataHora;
    }

    public AddressAudit(String cep, LocalDateTime dataHora) {

        this.cep = cep;
        this.dataHora = dataHora;
    }

    public String getCep() {
        return cep;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}