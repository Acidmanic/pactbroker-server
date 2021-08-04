/* 
 * The MIT License
 *
 * Copyright 2019 Mani Moayedi (acidmanic.moayedi@gmail.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.acidmanic.cicdassistant.utility;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class TextReformater {
    
    
    private interface Indenter{
        StringBuilder appendIndentedLine(StringBuilder sb,int indent);
    }
    
    private class StringIndenter implements Indenter{

        @Override
        public StringBuilder appendIndentedLine(StringBuilder sb, int indent) {
            sb.append("\n");

            for(int i=0;i<indent;i++){
                sb.append("    ");
            }

            return sb;
        }
    }
    
    
    private class HtmlIndenter implements Indenter{

        @Override
        public StringBuilder appendIndentedLine(StringBuilder sb, int indent) {
            sb.append("<br>");

            for(int i=0;i<indent;i++){
                sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
            }

            return sb;
        }
        
    }
    
    public String indentJson(String json){
        return indentJson(json, false);
    }
    
    public String indentJson(String json,boolean html){
        StringBuilder sb = new StringBuilder();
        Indenter indenter = html?new HtmlIndenter() : new StringIndenter();
        int indent =0;
        char[] chars = json.toCharArray();
        
        for(char ch: chars){
            if(ch=='{' || ch == '[' ){
                sb.append(ch);
                indent+=1;
                indenter.appendIndentedLine(sb,indent);
            }else if(ch=='}' || ch ==']'){
                indent -=1;
                indenter.appendIndentedLine(sb,indent);
                sb.append(ch);
            }else if ( ch == ','){
                sb.append(ch);
                indenter.appendIndentedLine(sb,indent);
            }
            else{
                sb.append(ch);
            }
        }
        
        
        return sb.toString();
    }
    
    public String plural(String noun){
        
        
        if (takesPluralE(noun)){
            return noun + "es";
        }
        
        return noun+"s";
    }

    private boolean takesPluralE(String noun) {
        String[] chars = {"s", "x", "z", "ch", "sh"};
        String lowerNoun = noun.toLowerCase();
        for(String end:chars){
            if (lowerNoun.endsWith(end)){
                return true;
            }
        }
        return false;
    }
}
