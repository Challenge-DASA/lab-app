package com.dasa.challenge.labapp.application.usecases.cart.impl;

import com.dasa.challenge.labapp.application.gateways.cart.CartGateway;
import com.dasa.challenge.labapp.application.usecases.cart.CartUseCase;
import com.dasa.challenge.labapp.domain.entities.Procedure;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.UUID;

public class CartUseCaseImpl implements CartUseCase {

    private final CartGateway cartGateway;

    public CartUseCaseImpl(CartGateway cartGateway) {
        this.cartGateway = cartGateway;
    }

    @Override
    public void add(Procedure procedure) {
        this.cartGateway.add(procedure);
    }

    @Override
    public void remove(UUID procedureId) {
        this.cartGateway.remove(procedureId);
    }

    @Override
    public ArrayList<Procedure> getAll() {
        return this.cartGateway.getAll();
    }

    @Override
    public Procedure get(UUID procedureId) {
        return this.cartGateway.get(procedureId);
    }

    @Override
    public ObservableList<Procedure> watchCart() {
        return this.cartGateway.watchCart();
    }
}
