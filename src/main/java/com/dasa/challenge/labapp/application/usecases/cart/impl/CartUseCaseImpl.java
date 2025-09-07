package com.dasa.challenge.labapp.application.usecases.cart.impl;

import com.dasa.challenge.labapp.application.gateways.proceduresCart.CartGateway;
import com.dasa.challenge.labapp.application.usecases.cart.CartUseCase;
import com.dasa.challenge.labapp.domain.entities.Procedure;

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
}
