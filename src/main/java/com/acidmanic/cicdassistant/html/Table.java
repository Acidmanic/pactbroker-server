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
public class Table extends ContainerHtmlTagBase{

    private Tr lastRow = null;
    
    
    
    @Override
    public ContainerHtmlTagBase addChild(Tag child) {
        
        if(child instanceof Tr){
            
            this.lastRow = (Tr) child;
            
            return super.addChild(child);
        }
        if(child instanceof Td){
            
            if(this.lastRow==null){
                
                this.goNextRow();
            }
            this.lastRow.addChild(child);
            
            return this;
        }
        Td tdChild = new Td();
        
        tdChild.addChild(child);
        
        this.addChild(tdChild);
        
        return this;
    }
    
    public Table goNextRow(){
        
        Tr tr = new Tr();
        
        this.lastRow = tr;
        
        super.addChild(tr);
        
        return this;
    }
    
}
