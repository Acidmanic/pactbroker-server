/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.storage;

import com.acidmanic.delegates.arg1.Function;
import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.localpactbroker.jsonstorage.JsonStorageBase;
import com.acidmanic.localpactbroker.utility.Result;
import com.acidmanic.pact.models.Pact;
import com.acidmanic.pactmodels.Contract;
import com.acidmanic.pactmodels.Interaction;
import java.nio.charset.Charset;
import java.util.Base64;

/**
 *
 * @author diego
 */
public class PactStorage extends JsonStorageBase<Pact> {

    public PactStorage(StorageFileConfigs fileConfigs, Logger logger) {
        super(fileConfigs.getPactFileStorage(), Pact.class, logger);
    }

    public boolean write(Pact pact) {

        return super.save(pact);
    }

    public Result<Pact> read() {
        Pact model = super.load();

        if (model != null) {
            
            return new Result<>(true, model);
        }
        return new Result<>();
    }
//
//    private Pact manipulateBody(Pact pact, Function<String, String> manipulate) {
//
//        for (Contract contract : pact.getContracts()) {
//            for (Interaction interaction : contract.getInteractions()) {
//
//                String body = interaction.getRequest().getBody();
//
//                body = manipulate.perform(body);
//
//                interaction.getRequest().setBodyAsString(body);
//
//                body = interaction.getResponse().getBody();
//
//                body = manipulate.perform(body);
//
//                interaction.getResponse().setBodyAsString(body);
//            }
//        }
//        return pact;
//    }
//
//    private static final Charset BODY_CHARSET = Charset.forName("UTF-8");
//
//    private Pact encodeBody(Pact pact) {
//
//        Pact manipulated = manipulateBody(pact, body -> encode(body));
//
//        return manipulated;
//    }
//
//    private Pact decodeBody(Pact pact) {
//
//        Pact manipulated = manipulateBody(pact, body -> decode(body));
//
//        return manipulated;
//    }
//
//    private String encode(String body) {
//        
//        if (body == null || body.length() == 0) {
//            return body;
//        }
//        byte[] bytes = body.getBytes(BODY_CHARSET);
//
//        byte[] encodedBytes = Base64.getEncoder().encode(bytes);
//
//        String encoded = new String(encodedBytes, BODY_CHARSET);
//
//        return encoded;
//    }
//
//    private String decode(String body) {
//        
//        if (body == null || body.length() == 0) {
//            return body;
//        }
//        byte[] bytes = body.getBytes(BODY_CHARSET);
//
//        byte[] decodedBytes = Base64.getDecoder().decode(bytes);
//
//        String decoded = new String(decodedBytes, BODY_CHARSET);
//
//        return decoded;
//    }
}
