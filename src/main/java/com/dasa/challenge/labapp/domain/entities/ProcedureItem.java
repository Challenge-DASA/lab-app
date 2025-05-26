package com.dasa.challenge.labapp.domain.entities;

public class ProcedureItem {
    private final String id;
    private final String name;
    private final Integer quantity;

    public ProcedureItem(String name, Integer quantity, String id) {
        this.name = name;
        this.quantity = quantity;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getId() {
        return id;
    }
}