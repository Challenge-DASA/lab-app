package com.dasa.challenge.labapp.infrastructure.gateways.auth;

import com.dasa.challenge.labapp.application.gateways.auth.AuthGateway;

import javax.smartcardio.*;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class RfidAuthGatewayImpl implements AuthGateway {
    private Consumer<UUID> onCardRead;

    public RfidAuthGatewayImpl() {
    }

    @Override
    public void validateAuth() {
        System.out.println("Starting RFID validation...");
        this.startListening();
    }

    @Override
    public void authHandler(Consumer<UUID> handler) {
        this.onCardRead = handler;
    }

    private void startListening() {
        Thread thread = new Thread(() -> {
            try {
                TerminalFactory factory = TerminalFactory.getDefault();
                List<CardTerminal> terminals = factory.terminals().list();

                if (terminals.isEmpty()) {
                    System.err.println("No RFID Reader found!");
                    return;
                }

                CardTerminal terminal = terminals.get(0);
                System.out.println("Using reader: " + terminal.getName());


                    terminal.waitForCardPresent(0); // bloqueia até cartão ser apresentado
                    Card card = terminal.connect("*");

                    try {
                        byte[] uid = tryGetUid(card);
                        UUID token = uid != null && uid.length > 0
                                ? UUID.nameUUIDFromBytes(uid)
                                : UUID.nameUUIDFromBytes(card.getATR().getBytes());

                        System.out.println("Card detected, UUID: " + token);

                        if (onCardRead != null) {
                            onCardRead.accept(token);
                        }
                    } finally {
                        card.disconnect(false);
                        terminal.waitForCardAbsent(0); // espera o cartão ser removido
                    }
            } catch (Exception e) {
                e.printStackTrace();
//                throw new RuntimeException(e);
            }
        });

        thread.start();
    }

    private byte[] tryGetUid(Card card) throws Exception {
        CardChannel channel = card.getBasicChannel();
        CommandAPDU getUidCmd = new CommandAPDU(new byte[]{(byte) 0xFF, (byte) 0xCA, 0x00, 0x00, 0x00});
        ResponseAPDU resp = channel.transmit(getUidCmd);
        return resp.getSW() == 0x9000 ? resp.getData() : null;
    }
}
