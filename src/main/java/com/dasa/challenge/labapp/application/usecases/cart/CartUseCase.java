package com.dasa.challenge.labapp.application.usecases.cart;

import com.dasa.challenge.labapp.domain.entities.Procedure;

import java.util.ArrayList;
import java.util.UUID;

public interface CartUseCase {

    void add(Procedure procedure);

    void remove(UUID procedureId);

    ArrayList<Procedure> getAll();

    Procedure get(UUID procedureId);

}
