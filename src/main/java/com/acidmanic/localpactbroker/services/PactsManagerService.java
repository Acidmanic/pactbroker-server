/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.services;

import com.acidmanic.delegates.Function;
import com.acidmanic.localpactbroker.controllers.HttpStatus;
import com.acidmanic.localpactbroker.controllers.Models;
import com.acidmanic.localpactbroker.models.Dto;
import com.acidmanic.localpactbroker.models.PactMap;
import com.acidmanic.localpactbroker.storage.PactMapStorage;
import com.acidmanic.localpactbroker.storage.PactStorage;
import com.acidmanic.localpactbroker.storage.TokenStorage;
import com.acidmanic.localpactbroker.utility.Result;
import com.acidmanic.pact.models.Pact;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diego
 */
public class PactsManagerService {

    private final PactStorage pactStorage;
    private final PactMapStorage pactMapStorage;

    public PactsManagerService(PactStorage pactStorage, PactMapStorage pactMapStorage, TokenStorage tokenStorage) {
        this.pactStorage = pactStorage;
        this.pactMapStorage = pactMapStorage;
    }

    private static synchronized <T> T runThreadSafe(Function<T> code) {

        return code.perform();
    }

    public Dto<Pact> pull() {

        return runThreadSafe(
                () -> {
                    Result<Pact> pactResult = this.pactStorage.read();

                    if (pactResult.isSuccessfull()) {
                        return new Dto(pactResult.getValue());
                    } else {
                        return Models.failureDto(HttpStatus.NOT_FOUND);
                    }
                }
        );
    }

    public Dto<String> push(Pact pact) {

        return runThreadSafe(
                () -> {

                    if (pact == null) {

                        return Models.failureDto(HttpStatus.BAD_REQUEST);
                    }
                    boolean success = this.pactStorage.write(pact);

                    if (success) {

                        return Models.SuccessDto();
                    } else {

                        return Models.failureDto(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
        );
    }

    public Dto<String> store(String tag, Pact pact) {

        return runThreadSafe(
                () -> {

                    if (pact == null || tag == null || tag.isEmpty()) {

                        return Models.failureDto(HttpStatus.BAD_REQUEST);
                    }
                    Result<PactMap> pactMapResult = pactMapStorage.read();

                    PactMap pactMap = pactMapResult.isSuccessfull() ? pactMapResult.getValue() : new PactMap();

                    if (pactMap.containsKey(tag)) {

                        pactMap.remove(tag);
                    }

                    pactMap.put(tag, pact);

                    boolean success = this.pactMapStorage.write(pactMap);

                    if (success) {

                        return Models.SuccessDto();
                    } else {

                        return Models.failureDto(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
        );

    }

    public Dto<List<String>> getTags() {

        return runThreadSafe(
                () -> {
                    Result<PactMap> pactMapResult = this.pactMapStorage.read();

                    List<String> tags = new ArrayList<>();

                    if (pactMapResult.isSuccessfull()) {

                        tags.addAll(pactMapResult.getValue().keySet());
                    }

                    return new Dto<>(tags);
                }
        );
    }

    public Dto<Pact> elect(String tag) {

        return runThreadSafe(
                () -> {

                    if (tag == null || tag.isEmpty()) {

                        return Models.failureDto(HttpStatus.BAD_REQUEST);
                    }

                    Result<PactMap> pactMapResult = this.pactMapStorage.read();

                    PactMap pactMap = pactMapResult.isSuccessfull() ? pactMapResult.getValue() : new PactMap();

                    if (pactMap.containsKey(tag)) {

                        Pact pact = pactMap.get(tag);

                        boolean success = this.pactStorage.write(pact);

                        if (success) {

                            this.pactMapStorage.write(new PactMap());

                            return new Dto(pact);
                        }
                    }
                    return Models.failureDto(HttpStatus.NOT_FOUND);
                }
        );
    }

}
