/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.acidmanic.localpactbroker.utility;

/**
 *
 * @author diego
 * @param <T>
 */
public class Result<T> {
    
    private boolean successfull;
    private T value;
    
    public boolean isSuccessfull() {
        return successfull;
    }
    
    public void setSuccessfull(boolean successfull) {
        this.successfull = successfull;
    }
    
    public T getValue() {
        return value;
    }
    
    public void setValue(T value) {
        this.value = value;
    }
    
    /***
     * Constructing a result object with no arguments, will create a failure result
     */
    public Result() {
        this.successfull = false;
        this.value = null;
    }
    
    public Result(boolean successfull, T value) {
        this.successfull = successfull;
        this.value = value;
    }
    /**
     * Constructing a Result with a value argument, will create a successful result
     * @param value
     */
    public Result(T value) {
        this.value = value;
        this.successfull=true;
    }
    
    
}
