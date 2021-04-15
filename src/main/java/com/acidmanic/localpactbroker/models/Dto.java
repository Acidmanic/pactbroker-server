/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.models;

/**
 *
 * @author diego
 * @param <TModel> data to be transferred
 */
public class Dto<TModel> {

    private TModel model;
    private boolean failure = false;
    private Object error = new Object();

    public Dto() {
    }

    public Dto(TModel model) {
        this.model = model;
        failure = false;
        error = new Object();
    }

    public TModel getModel() {
        return model;
    }

    public void setModel(TModel model) {
        this.model = model;
    }

    public boolean isFailure() {
        return failure;
    }

    public void setFailure(boolean failure) {
        this.failure = failure;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

}
