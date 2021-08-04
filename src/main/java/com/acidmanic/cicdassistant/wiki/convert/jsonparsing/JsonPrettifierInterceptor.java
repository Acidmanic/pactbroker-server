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
public class JsonPrettifierInterceptor extends JsonInterceptorBase {

    private String result;
    private final Indent indent;

    public JsonPrettifierInterceptor() {
        this.result = "";
        this.indent = new Indent("    ");
    }

    public JsonPrettifierInterceptor(String indentUnit) {
        this.result = "";
        this.indent = new Indent(indentUnit);
    }

    @Override
    protected void deliver(String text, JsonStringState state) {
        if (isSymbol(state)) {
            if ("{".equals(text)) {
                indent.in();
                text += "\n" + indent.toString();
            }
            if ("}".equals(text)) {
                indent.out();
                text = "\n" + indent.toString() + text;
            }
            if (":".equals(text)) {
                text += " ";
            }
            if (",".equals(text)) {
                text += "\n" + indent.toString();
            }
        }
        this.result += text;
        
        onText(text, state);
    }

    public String getPrettifiedJson() {
        return result;
    }
    
    protected void onText(String text, JsonStringState state){
        
    }
    

}
