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
public class Html extends ContainerHtmlTagBase{

    
    private final Head head;
    private final Title title;
    private final Body body;
    
    public Html() {
        
        this.head = new Head();
        this.body = new Body();
        this.title = new Title();
        
        this.head.addChild(this.title);
        
        super.addChild(this.head);
        super.addChild(this.body);
        
    }
    
    @Override
    protected String tagName() {
        return "html";
    }

    @Override
    protected boolean isSingleTag() {
        return false;
    }

    public Head getHead() {
        return head;
    }

    public Body getBody() {
        return body;
    }
    
    
    public String getTitle(){
        
        return this.title.getTitle();
    }
    
    public void setTitle(String value){
        
        this.title.setTitle(value);
    }
    
}
