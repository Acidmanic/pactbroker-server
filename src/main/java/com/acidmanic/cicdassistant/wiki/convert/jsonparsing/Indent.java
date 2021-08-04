/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.wiki.convert.jsonparsing;

/**
 *
 * @author diego
 */
class Indent {
    
    private final String unit;
    private int indent = 0;

    public Indent(String unit) {
        this.unit = unit;
    }

    public void in() {
        this.indent += 1;
    }

    public void out() {
        if (this.indent > 0) {
            this.indent -= 1;
        }
    }

    public StringBuilder append(StringBuilder sb) {
        for (int i = 0; i < this.indent; i++) {
            sb.append(this.unit);
        }
        return sb;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        append(sb);
        return sb.toString();
    }
    
}
