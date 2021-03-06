/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.controllers.models;

import com.acidmanic.cicdassistant.controllers.HttpStatus;
import com.acidmanic.cicdassistant.models.Dto;

/**
 *
 * @author diego
 */
public class Models {

    public static <T> Dto<T> failureDto(String message) {
        Dto<T> failure = new Dto<>();

        failure.setError(message);
        failure.setFailure(true);

        return failure;
    }

    public static <T> Dto<T> failureDto(Exception message) {
        Dto<T> failure = new Dto<>();

        failure.setError(message);
        failure.setFailure(true);

        return failure;
    }

    public static <T> Dto<T> failureDto(HttpStatus status) {
        Dto<T> failure = new Dto<>();

        failure.setError(status);
        failure.setFailure((status.getCode()/100)!=2);

        return failure;
    }

    public static <T> Dto<T> SuccessDto() {
        Dto<T> failure = new Dto<>();

        failure.setError(HttpStatus.OK);
        failure.setFailure(false);

        return failure;
    }
}
