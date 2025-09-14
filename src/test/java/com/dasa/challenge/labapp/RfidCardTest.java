package com.dasa.challenge.labapp;

import org.junit.jupiter.api.Test;

import javax.smartcardio.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class RfidCardTest {

//    private static String bytesToHex(byte[] bytes) {
//        if (bytes == null || bytes.length == 0) return "";
//        StringBuilder sb = new StringBuilder();
//        for (byte b : bytes) {
//            sb.append(String.format("%02X", b));
//        }
//        return sb.toString();
//    }
//
//    @Test
//    public void testGenerateUuidFromCard() throws Exception {
//        TerminalFactory factory = TerminalFactory.getDefault();
//        var terminals = factory.terminals().list();
//
//        assertNotNull(terminals, "Lista de terminais retornou nulo");
//        if (terminals.isEmpty()) {
//            System.out.println("Nenhum leitor encontrado. Verifique drivers / conexão USB.");
//            return;
//        }
//
//        CardTerminal terminal = terminals.get(0);
//        System.out.println("Usando leitor: " + terminal.getName());
//
//        // aguarda até 15 segundos para um cartão ser apresentado
//        boolean cardPresent = terminal.waitForCardPresent(15_000);
//        if (!cardPresent) {
//            System.out.println("Nenhum cartão detectado dentro do timeout (15s).");
//            return;
//        }
//
//        Card card = null;
//        try {
//            // conecta com qualquer protocolo
//            card = terminal.connect("*");
//            System.out.println("Cartão conectado. ATR: " + bytesToHex(card.getATR().getBytes()));
//
//            CardChannel channel = card.getBasicChannel();
//
//            // Comando vendor para obter UID (funciona em muitos leitores tipo ACR122U)
//            var getUidCmd = new CommandAPDU(new byte[]{
//                    (byte) 0xFF, (byte) 0xCA, 0x00, 0x00, 0x00
//            });
//
//            ResponseAPDU resp = channel.transmit(getUidCmd);
//
//            int sw = resp.getSW(); // status word
//            byte[] uid = resp.getData();
//
//            if (uid != null && uid.length > 0 && sw == 0x9000) {
//                System.out.println("UID raw (hex): " + bytesToHex(uid));
//                // Cria um UUID determinístico a partir dos bytes do UID
//                UUID cardUuid = UUID.nameUUIDFromBytes(uid);
//                System.out.println("UUID gerado (nameUUIDFromBytes): " + cardUuid);
//                assertNotNull(cardUuid);
//            } else {
//                System.out.println("Não foi possível obter UID via FF CA (SW=" + Integer.toHexString(sw) +
//                        ", dataLen=" + (uid == null ? 0 : uid.length) + ")");
//                // Fallback: use ATR para gerar um identificador (não ideal, mas determinístico)
//                byte[] atr = card.getATR().getBytes();
//                System.out.println("Usando ATR (hex) para gerar UUID: " + bytesToHex(atr));
//                UUID atrUuid = UUID.nameUUIDFromBytes(atr);
//                System.out.println("UUID from ATR: " + atrUuid);
//                assertNotNull(atrUuid);
//            }
//
//        } finally {
//            if (card != null) {
//                try {
//                    card.disconnect(false);
//                } catch (CardException ignored) {
//                }
//            }
//            // aguarda remoção do cartão antes de terminar (opcional)
//            terminal.waitForCardAbsent(2_000);
//        }
//    }

}
