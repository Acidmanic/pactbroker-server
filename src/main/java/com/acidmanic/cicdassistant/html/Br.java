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
public class Br extends HtmlTagBase{

    @Override
    protected boolean isSingleTag() {
        return true;
    }

    @Override
    protected StringBuilder appendContent(StringBuilder sb) {
        return sb;
    }
    
}
