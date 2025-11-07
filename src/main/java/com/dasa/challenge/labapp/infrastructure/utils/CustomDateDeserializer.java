package com.dasa.challenge.labapp.infrastructure.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CustomDateDeserializer extends JsonDeserializer<LocalDateTime> {
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String date = p.getText();
        return LocalDateTime.parse(date, formatter)
                .atZone(ZoneId.of("GMT"))
                .withZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
