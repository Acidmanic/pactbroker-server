/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.utility;

/**
 *
 * @author diego
 * @param <T>
 */
public class Final<T> {

    private T value;

    public Final(T value) {
        this.value = value;
    }

    public Final() {
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
