package com.dasa.challenge.labapp.infrastructure.gateways.cart;

import com.dasa.challenge.labapp.application.gateways.cart.CartGateway;
import com.dasa.challenge.labapp.domain.entities.Procedure;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.UUID;
import java.util.Optional;

public class LocalCartGatewayImpl implements CartGateway {

    private final ObservableList<Procedure> cart = FXCollections.observableArrayList();

    @Override
    public void add(Procedure procedure) {
        if (!cart.contains(procedure)) {
            this.cart.add(procedure);
        }
    }

    @Override
    public void remove(UUID procedureId) {
        this.cart.removeIf(procedure -> procedure.getId().equals(procedureId.toString()));
    }

    @Override
    public ArrayList<Procedure> getAll() {
        return new ArrayList<>(this.cart);
    }

    @Override
    public ObservableList<Procedure> watchCart() {
        return this.cart;
    }

    @Override
    public Procedure get(UUID procedureId) {
        Optional<Procedure> procedure = this.cart.stream()
                .filter(p -> p.getId().equals(procedureId.toString()))
                .findFirst();

        return procedure.orElse(null);
    }

}