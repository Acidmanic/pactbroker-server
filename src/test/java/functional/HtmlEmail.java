/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functional;

import com.acidmanic.cicdassistant.html.Body;

/**
 *
 * @author diego
 */
public class HtmlEmail {
    
    
    
    
    public static void main(String[] args){
        
        Body body = new Body();
        
        
        
        String htmlBody = body.toString();
        
        System.out.println(htmlBody);
    }
}
