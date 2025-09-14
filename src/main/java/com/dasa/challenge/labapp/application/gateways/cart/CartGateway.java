package com.dasa.challenge.labapp.application.gateways.cart;

import com.dasa.challenge.labapp.domain.entities.Procedure;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.UUID;

public interface CartGateway {

    void add(Procedure procedure);

    void remove(UUID procedureId);

    ArrayList<Procedure> getAll();

    Procedure get(UUID procedureId);

    ObservableList<Procedure> watchCart();

}
