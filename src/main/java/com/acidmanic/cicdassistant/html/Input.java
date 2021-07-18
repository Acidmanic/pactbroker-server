/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.html;

/**
 *
 * @author diego
 */
public class Input extends HtmlTagBase{
    
    
    public Input() {
        
    
    }

    @Override
    protected boolean isSingleTag() {
        return true;
    }

    @Override
    protected StringBuilder appendContent(StringBuilder sb) {
        return sb;
    }

    public String getType() {
        
        return getAttribute("type");
    }

    public void setType(String type) {
        
        setAttribute("type", type);
    }

    public String getValue() {
        
        return getAttribute("value");
    }

    public void setValue(String value) {
        
        setAttribute("value", value);
    }
    
    
    
}
