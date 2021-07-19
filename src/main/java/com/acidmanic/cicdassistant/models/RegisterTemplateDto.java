/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.models;

/**
 *
 * @author diego
 */
public class RegisterTemplateDto {

    private String templateName;
    private String base64Content;

    public RegisterTemplateDto(String templateName, String base64HtmlContent) {
        this.templateName = templateName;
        this.base64Content = base64HtmlContent;
    }

    public RegisterTemplateDto() {
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getBase64Content() {
        return base64Content;
    }

    public void setBase64Content(String base64Content) {
        this.base64Content = base64Content;
    }

}
