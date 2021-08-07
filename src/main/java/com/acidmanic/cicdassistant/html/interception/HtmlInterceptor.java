/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.html.interception;

import org.jsoup.nodes.Document;

/**
 *
 * @author diego
 */
public interface HtmlInterceptor {
    
    
    void manipulate (Document document);
}
