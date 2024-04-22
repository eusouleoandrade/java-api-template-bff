package com.project.bff.domain.common;

public abstract class BaseEntity<TId extends Number> {

    protected TId id;

    public TId getId() {
        return id;
    }

    public void setId(TId id) {
        this.id = id;
    }
}